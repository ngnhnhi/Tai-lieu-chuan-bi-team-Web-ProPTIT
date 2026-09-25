package com.example.profile.controller;

import com.example.profile.model.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller // Đánh dấu đây là Spring MVC Controller (trả về View, khác với @RestController trả về JSON) -> Cho Spring biết class này chịu trách nhiệm điều hướng trang web.
public class ProfileController {

    @GetMapping("/") // Bắt request khi vào trang chủ http://localhost:8080/
    public String showProfile(Model model) {
        // 1. Tạo danh sách kỹ năng mẫu
        List<String> mySkills = Arrays.asList(
                "Đi dạo",
                "Làm bánh",
                "Xem phim"
        );

        // 2. Khởi tạo Object Profile với dữ liệu hardcode mẫu
        Profile myProfile = new Profile(
                "Nguyễn Hạnh Nhi",
                "Sinh viên Công nghệ thông tin - PTIT",
                "Sinh viên Công nghệ thông tin tại Học viện Công nghệ Bưu chính Viễn thông. "
                        + "Tôi yêu thích lập trình và đang tập trung phát triển nền tảng Java, "
                        + "Spring Boot, cơ sở dữ liệu và tìm hiểu thêm về Data và AI.",
                "nhnhivipbb@gmail.com",
                "https://github.com/ngnhnhi",
                mySkills
        );

        // 3. Gắn Object vào Model của Spring MVC
        // Key "profile" này sẽ được file HTML Thymeleaf dùng để gọi dữ liệu: ${profile.fullName}
        model.addAttribute("profile", myProfile);

        // 4. Trả về tên template HTML (Spring Boot sẽ tự tìm file profile.html trong templates/)
        return "profile";
    }
}