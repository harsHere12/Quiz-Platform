package UserQuizManagement.demoUserQuiz.Controller;



import UserQuizManagement.demoUserQuiz.Entity.Score;
import UserQuizManagement.demoUserQuiz.Service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("scores")
public class ScoresController {

    @Autowired
    private ScoreService scoreService;

    public ScoresController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

//    @GetMapping
//    List<Score> getALlScores(){
//        return scoreService.getAllScores() ;
//    }
//
//    @GetMapping (path = "/user/{userId}")
//    List <Score
//
    @PostMapping
    Score addScore(@RequestBody Score score){

        System.out.println(score);
        return scoreService.addScore(score);
    }



}
