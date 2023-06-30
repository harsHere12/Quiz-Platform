package UserQuizManagement.demoUserQuiz.Service;



import UserQuizManagement.demoUserQuiz.Entity.Score;
import UserQuizManagement.demoUserQuiz.Repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository ;
    @Autowired
    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public List<Score> getAllScores() {
        return scoreRepository.findAll() ;
    }

    public List <Score> findAllScoresById(Long userId){
        return scoreRepository.findAllScoresById(userId) ;
    }

    public Score addScore(Score score) {
        return scoreRepository.save(score) ;
    }

    public Score getScore(Long userId, Long TopicId){

        List<Score> score =  scoreRepository.findScore(userId,TopicId);
        if(score.isEmpty()) return null ;
        else return score.get(0) ;
    }

//    public List<Score> getAllScoresOnTopic(Long topicId) {
//        return scoreRepository.findAllScoreByTopicId(topicId) ;
//    }

    //    ,Sort.by(Sort.Direction.DESC,"score")
//    public List<Score> getAllTopicScores(Long topicId) {
//
//        return scoreRepository.findAllScoresTOfTopic(topicId);
//
//    }

//    public void getScoreOfUserOnTopic(Long userId, Long topicId) {
//        System.out.println(scoreRepository.getScoreOfUserOnTopic(userId,topicId)); ;
//

}
