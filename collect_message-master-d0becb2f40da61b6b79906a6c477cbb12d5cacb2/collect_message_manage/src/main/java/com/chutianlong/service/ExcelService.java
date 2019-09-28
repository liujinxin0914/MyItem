package com.chutianlong.service;

import com.chutianlong.pojo.Student;
import com.chutianlong.pojo.Sys_organize;
import com.chutianlong.pojo.Teacher;
import net.sf.json.JSON;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface ExcelService {

    List<Sys_organize> findSchool(int pid);

    List<Sys_organize> findNj(int pid);

    List<Sys_organize> findBj(int pid);

    Map<String, Object> studentExcelDR(HttpSession session, MultipartFile productData,Model model);

    Map<String, Object> teacherExcelDR(HttpSession session, MultipartFile productData,Model model);

    Map findStudentBy(String school, String nj, String bj,String name, String Yj,String code, HttpServletResponse response, String pageNum);

    List<Sys_organize> findYj(int pid);


    void export(String teacherListError, HttpServletResponse response,String message);

    void exportStudent(String studentListError, HttpServletResponse response,String message);
}
