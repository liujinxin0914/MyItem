package com.neteduid.common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.util.Arrays;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.jcajce.spec.SM2ParameterSpec;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.util.encoders.Hex;

/**
 * 国密证书工具类
 * 
 * @author 黄继
 *
 */
public class GmUtil {

	private static X9ECParameters x9ECParameters = GMNamedCurves
			.getByName("sm2p256v1");
	
	private static ECParameterSpec ecParameterSpec = new ECParameterSpec(
			x9ECParameters.getCurve(), x9ECParameters.getG(),
			x9ECParameters.getN());

	private final static int RS_LEN = 32;

	static {
		if (Security.getProvider("BC") == null) {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		}
	}

	/**
	 * 从pfx证书文件中解析公私钥
	 * 
	 * @param pfx pfx文件
	 * 
	 * @param pwd pfx证书文件密码
	 * 
	 * @return
	 */
	public static KeyStore getKeyStoreByPfx(byte[] pfx, String pwd) {
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12", "BC");
			InputStream in = new ByteArrayInputStream(pfx);
			ks.load(in, pwd.toCharArray());
			return ks;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 签名
	 *
	 * @param srcData 源数据
	 * 
	 * @param id 应用ID
	 * 
	 * @param privateKey 密钥
	 * 
	 * @return r||s，直接拼接byte数组的rs
	 */
	public static byte[] signSm3WithSm2(byte[] srcData, byte[] id,
			PrivateKey privateKey) {
		return rsAsn1ToPlainByteArray(signSm3WithSm2Asn1Rs(srcData, id, privateKey));
	}

	/**
	 * 签名
	 * 
	 * @param srcData 源数据
	 * 
	 * @param id 应用ID
	 * 
	 * @param privateKey 密钥
	 * 
	 * @return rs in 
	 */
	public static byte[] signSm3WithSm2Asn1Rs(byte[] srcData, byte[] id,
			PrivateKey privateKey) {
		try {
			SM2ParameterSpec parameterSpec = new SM2ParameterSpec(id);
			Signature signer = Signature.getInstance("SM3withSM2", "BC");
			signer.setParameter(parameterSpec);
			signer.initSign(privateKey, new SecureRandom());			
			signer.update(srcData, 0, srcData.length);
			byte[] sig = signer.sign();
			return sig;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 验签
	 *
	 * @param srcData 源数据
	 * 
	 * @param id 应用ID
	 * 
	 * @param rs 签名数据;r||s，直接拼接byte数组的rs
	 *            
	 * @param publicKey 公钥
	 * 
	 * @return
	 */
	public static boolean verifySm3WithSm2(byte[] srcData, byte[] id,
			byte[] rs, PublicKey publicKey) {
		return verifySm3WithSm2Asn1Rs(srcData, id, rsPlainByteArrayToAsn1(rs), publicKey);
	}

	/**
	 * 验签
	 * 
	 * @param srcData 源数据
	 * 
	 * @param id 应用ID
	 * 
	 * @param rs 签名数据
	 * 
	 * @param publicKey 公钥
	 * 
	 * @return
	 */
	public static boolean verifySm3WithSm2Asn1Rs(byte[] srcData, byte[] id,
			byte[] rs, PublicKey publicKey) {
		try {
			SM2ParameterSpec parameterSpec = new SM2ParameterSpec(id);
			Signature verifier = Signature.getInstance("SM3withSM2", "BC");
			verifier.setParameter(parameterSpec);
			verifier.initVerify(publicKey);
			verifier.update(srcData, 0, srcData.length);
			return verifier.verify(rs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * BC的SM3withSM2验签需要的rs是asn1格式的，这个方法将直接拼接r||s的字节数组转化成asn1格式
	 * 
	 * @param sign
	 *            in plain byte array
	 * @return rs result in asn1 format
	 */
	private static byte[] rsPlainByteArrayToAsn1(byte[] sign) {
		if (sign.length != RS_LEN * 2)
			throw new RuntimeException("err rs. ");
		BigInteger r = new BigInteger(1, Arrays.copyOfRange(sign, 0, RS_LEN));
		BigInteger s = new BigInteger(1, Arrays.copyOfRange(sign, RS_LEN,
				RS_LEN * 2));
		ASN1EncodableVector v = new ASN1EncodableVector();
		v.add(new ASN1Integer(r));
		v.add(new ASN1Integer(s));
		try {
			return new DERSequence(v).getEncoded("DER");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * BC的SM3withSM2签名得到的结果的rs是asn1格式的，这个方法转化成直接拼接r||s
	 * 
	 * @param rsDer
	 *            rs in asn1 format
	 * @return sign result in plain byte array
	 */
	private static byte[] rsAsn1ToPlainByteArray(byte[] rsDer) {
		ASN1Sequence seq = ASN1Sequence.getInstance(rsDer);
		byte[] r = bigIntToFixexLengthBytes(ASN1Integer.getInstance(
				seq.getObjectAt(0)).getValue());
		byte[] s = bigIntToFixexLengthBytes(ASN1Integer.getInstance(
				seq.getObjectAt(1)).getValue());
		byte[] result = new byte[RS_LEN * 2];
		System.arraycopy(r, 0, result, 0, r.length);
		System.arraycopy(s, 0, result, RS_LEN, s.length);
		return result;
	}

	private static byte[] bigIntToFixexLengthBytes(BigInteger rOrS) {
		// for sm2p256v1, n is
		// 00fffffffeffffffffffffffffffffffff7203df6b21c6052b53bbf40939d54123,
		// r and s are the result of mod n, so they should be less than n and
		// have length<=32
		byte[] rs = rOrS.toByteArray();
		if (rs.length == RS_LEN)
			return rs;
		else if (rs.length == RS_LEN + 1 && rs[0] == 0)
			return Arrays.copyOfRange(rs, 1, RS_LEN + 1);
		else if (rs.length < RS_LEN) {
			byte[] result = new byte[RS_LEN];
			Arrays.fill(result, (byte) 0);
			System.arraycopy(rs, 0, result, RS_LEN - rs.length, rs.length);
			return result;
		} else {
			throw new RuntimeException("err rs: " + Hex.toHexString(rs));
		}
	}

}
