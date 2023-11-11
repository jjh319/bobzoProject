package org.zerock.myapp.controller;


import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.myapp.domain.*;
import org.zerock.myapp.service.CategoriesService;
import org.zerock.myapp.service.CommentsService;
import org.zerock.myapp.service.RecipeService;
import org.zerock.myapp.service.UsersService;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Log4j2
@NoArgsConstructor


@RequestMapping("/recipe/*")
@Controller
public class RecipeController {


    @Setter(onMethod_ = @Autowired)
    private RecipeService recipeService;

    @Setter(onMethod_ = @Autowired)
    private CategoriesService categoriesService;

    @Setter(onMethod_ = @Autowired)
    private CommentsService commentsService;

    @Setter(onMethod_ = @Autowired)
    private UsersService usersService;

    @GetMapping("/write")
    String write(Model model){
        log.trace("write() Invoked.");

        model.addAttribute("recipeDTO", new RecipeDTO()); // 폼을 위한 빈 RecipeDTO 생성
        return "/recipe/write"; // Thymeleaf 템플릿 이름

    } // write 글작성


    @PostMapping("/updateRecipe")                           // 이것만 하면 userid가 세팅이 안된다 그래서 Principal 로 로그인된 UserId를 받아온다.
    public String updateRecipe(@ModelAttribute("recipeDTO") RecipeDTO recipeDTO, Principal principal){

        if(principal != null) {
            String loggedInUserId = principal.getName();
            recipeService.recipeWrite(recipeDTO, loggedInUserId);
        } // if

        return "redirect:/recipe/page/1";
    } // updateRecipe


    @GetMapping("/page/{page}")
    public String showPagedRecipeList(@PathVariable("page") int page, Model model){
        int pageSize = 10;                     //    1페이지부터 시작 page-1 -> 0p
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "num"));

        Page<Recipe> recipePage = recipeService.getPagedRecipes(pageable);

        model.addAttribute("recipes", recipePage);
        return "recipe/page";
    } // showPagedRecipeList


    @GetMapping("/readRecipe/{recipeNum}")
    public String readRecipe(@PathVariable("recipeNum") Long recipeNum, Model model) {
        recipeService.updateViewCount(recipeNum);

        Recipe recipe = recipeService.getRecipeByNum(recipeNum);
        model.addAttribute("recipe", recipe);

        List<Comments> comments = commentsService.getCommentsByRecipe(recipe); // CommentsService를 생성하여 댓글 가져오기

        model.addAttribute("comments", comments); // 댓글을 모델에 추가

        return "recipe/readRecipe";
    }


    @GetMapping("/edit/{recipeNum}")
    public String editRecipe(@PathVariable("recipeNum") Long recipeNum, Principal principal, Model model) {
        Recipe recipe = recipeService.getRecipeByNum(recipeNum);

        if (recipe != null && principal != null) {
            String loggedInUserId = principal.getName();

            // 로그인한 사용자가 레시피의 소유자인지 확인합니다.
            if (recipe.getFkUsers().getUserId().equals(loggedInUserId)) {
                model.addAttribute("recipe", recipe); // 올바른 레시피를 모델에 추가
                return "recipe/editRecipe";
            }
        }

        return "redirect:/recipe/page/1";
    }


    @PostMapping("/modifyRecipe/{recipeNum}")
    public String modifyRecipe(@ModelAttribute("recipe") Recipe recipe, Principal principal) {
        if (principal != null) {
            String loggedInUserId = principal.getName();
            Recipe existingRecipe = recipeService.getRecipeByNum(recipe.getNum());

            if (existingRecipe != null && existingRecipe.getFkUsers().getUserId().equals(loggedInUserId)) {
                // 레시피 세부 정보 업데이트
                existingRecipe.setTitle(recipe.getTitle());
                existingRecipe.setContent(recipe.getContent());

                String selectedCategoryName = recipe.getFkCategories().getName(); // Categories 객체에서 카테고리 이름을 가져옵니다.
                Categories selectedCategories = categoriesService.getCategoryByName(selectedCategoryName);
                if (selectedCategories != null) {
                    existingRecipe.setFkCategories(selectedCategories);
                }

                // 업데이트된 레시피 저장
                recipeService.updateRecipe(existingRecipe);

                return "redirect:/recipe/page/1";
            } // inner if
        } // if

        return "redirect:/recipe/page/1";
    } // modifyRecipe



    @PostMapping("/addComment/{recipeNum}")
    public String addComment(
            @PathVariable("recipeNum") Long recipeNum,
            @RequestParam("comment") String commentContent,
            Principal principal
    ) {
        // 이 메서드는 POST 요청을 처리하며, "/recipe/addComment/{recipeNum}" 엔드포인트로부터 호출됩니다.
        // {recipeNum} 경로 변수는 현재 상세 페이지의 레시피 ID를 나타냅니다.
        // 댓글 내용은 요청 매개변수로 전달됩니다.

        // 댓글 내용, 사용자 및 레시피 정보를 사용하여 Comments 엔터티를 생성하고 저장해야 합니다.
        Comments comment = new Comments();
        comment.setComments(commentContent);

        // 현재 로그인한 사용자 이름을 가져와서 댓글 작성자로 설정합니다.
        String loggedInUsername = principal.getName();
        Users loggedInUser = usersService.getUserById(loggedInUsername);
        comment.setUsers(loggedInUser);

        // 레시피 ID를 사용하여 해당 레시피를 찾습니다.
        Recipe recipe = recipeService.getRecipeByNum(recipeNum);

        if (recipe != null) {
            // 레시피와 댓글을 연결합니다.
            comment.setRecipe(recipe);

            // CommentsService를 사용하여 댓글을 데이터베이스에 저장합니다.
            commentsService.addComment(comment);

            // 레시피 상세 페이지로 리디렉션합니다.
            return "redirect:/recipe/readRecipe/" + recipeNum;
        } else {
            // 레시피가 없는 경우 오류 페이지 또는 기타 처리를 수행할 수 있습니다.
            return "redirect:/error";
        }
    }


//    ======================================= 레시피 댓글 수정 로직 =======================================

    @GetMapping("/updateComment/{commentId}")
    public String updateComment(@PathVariable("commentId") Long commentId, Principal principal, Model model) {
        Comments comment = commentsService.getCommentById(commentId);

        String loggedInUsername = principal.getName();
        String commentAuthor = comment.getFkUsers().getUserId();

        if (loggedInUsername.equals(commentAuthor)) {
            model.addAttribute("comment", comment);
            return "recipe/updateComment";  // 수정 폼 템플릿
        } else {
            Long recipeNum = comment.getFkRecipe().getNum(); // 레시피 번호 가져오기
            return "redirect:/recipe/readRecipe/" + recipeNum;
        }
    }


    @PostMapping("/updateComment")
    public String updateComment(
            @RequestParam("commentId") Long commentId,
            @RequestParam("comments") String content) {
        commentsService.updateComment(commentId, content);
        // 수정된 댓글의 recipeNum을 가져와서 해당 레시피 상세 페이지로 리디렉션
        Comments updatedComment = commentsService.getCommentById(commentId);
        Long recipeNum = updatedComment.getFkRecipe().getNum(); // 레시피 번호 가져오기
        return "redirect:/recipe/readRecipe/" + recipeNum;
    }


    @PostMapping("/deleteComment/{commentId}")
    public String postDeleteComment(@PathVariable("commentId") Long commentId, Principal principal) {
        Comments comment = commentsService.getCommentById(commentId);
//        log.trace("\t★○ commentId ★☆ : {}", commentId);
        log.trace("\t★○ comment ★☆ : {}", comment);

        String loggedInUsername = principal.getName();

//        log.trace("\t★○ 가져온 아이디 ★☆ : {}", comment.getFkUsers().getUserId());
//        log.trace("\t★○ 로그인중 아이디 ★☆ : {}", loggedInUsername);
        log.trace("\t★○ 로그인중 아이디 ★☆ : {}", comment.getFkUsers().getUserId().equals(loggedInUsername));
        if (comment != null && comment.getFkUsers().getUserId().equals(loggedInUsername)) {
            log.info("실행");
            commentsService.deleteComment(commentId);
        }else{
            log.info("실행안됨");
        }

        // 댓글이 삭제된 레시피 상세 페이지로 리디렉션
        return "redirect:/recipe/readRecipe/" + comment.getFkRecipe().getNum();
    }

} // end class
