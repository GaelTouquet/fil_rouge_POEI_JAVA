package com.example.itraining_api.controller;

import com.example.itraining_api.dto.SessionDTO;
import com.example.itraining_api.entity.*;
import com.example.itraining_api.repository.TeacherAccountRepository;
import com.example.itraining_api.service.AdministratorAccountService;
import com.example.itraining_api.service.SessionService;
import com.example.itraining_api.service.TeacherAccountService;
import com.example.itraining_api.service.TrainingModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private TeacherAccountRepository teacherAccountRepository;

    @Autowired
    private TeacherAccountService teacherAccountService;

    @Autowired
    private TrainingModuleService trainingModuleService;


    @GetMapping("/allSession")
    public ResponseEntity<Map<String, List<Session>>> findAllSession(){
        Map<String, List<Session>> hashMap = new HashMap<String, List<Session>>();
        try{
            hashMap.put ("Sessions récupérées", sessionService.findSessionList());
        } catch (Exception e) {
            hashMap.put("Erreur à cause de " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(hashMap);
        }
        return ResponseEntity.ok(hashMap);
    }
    @GetMapping("/session/{id}")
    public ResponseEntity<Map<String, Session>> findSession(@PathVariable int id) {
        Map<String, Session> hashMap = new HashMap<String, Session>();
        try {
            hashMap.put("Session trouvée", sessionService.findSessionById(id));
        } catch (Exception e) {
            hashMap.put("Erreur à cause de " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(hashMap);
        }
        return ResponseEntity.ok(hashMap);
    }

    @PostMapping("/addSession")
    public ResponseEntity<Map<String, Session>> saveSession(@RequestBody SessionDTO sessionDTO){
        Map<String, Session> hashMap = new HashMap<String, Session>();
        try{
            TeacherAccount teacherAccount = teacherAccountService.findTeacherById(sessionDTO.teacherId);
            Training training = trainingModuleService.findTrainingModuleById(sessionDTO.trainingSessionId);
            Session session = new Session(teacherAccount, sessionDTO.getStartDate(), sessionDTO.getEndDate(), sessionDTO.room, sessionDTO.status, training);
            Session sessionTest = sessionService.saveSession(session);
            System.out.println("Session test"+sessionTest);
            hashMap.put("Session ajoutée", sessionService.saveSession(session));
        } catch(Exception e){
            hashMap.put("Erreur à cause de " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(hashMap);
        }
        return ResponseEntity.ok(hashMap);
    }

    @PutMapping("/updateSession/{id}")
    public ResponseEntity<Map<String, Session>> updateSession(@PathVariable("id") int id, @RequestBody Session session){
        Map<String, Session> hashMap = new HashMap<String, Session>();
        try{
            hashMap.put("Session modifiée",sessionService.updateSession(session, id));
        } catch (Exception e) {
            hashMap.put("Erreur à cause de " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(hashMap);
        }
        return ResponseEntity.ok(hashMap);
    }


    @DeleteMapping("/suppressSession/{id}")
    public ResponseEntity<String> deleteSession(@PathVariable("id") int id){
        Map<String, String> hashMap = new HashMap<String, String>();
        try{
            sessionService.deleteSessionById(id);
                hashMap.put("Session supprimée", "Deleted");
        } catch(Exception e){
            hashMap.put("Erreur à cause de " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception has occurred due to " + e.getMessage());
        }
        return ResponseEntity.ok("Session supprimé");
    }

    @PostMapping("/addTeacherForASession")
    public ResponseEntity<Map<String, TeacherAccount>> addTeacher(@RequestParam int sessionId, @RequestParam int teacherId){
        Map<String, TeacherAccount> hashMap = new HashMap<String, TeacherAccount>();
        try {
            Session session = sessionService.findSessionById(sessionId);
            TeacherAccount teacherAccount = teacherAccountService.findTeacherById(teacherId);
            if (session == null) {
                hashMap.put("La session n'a pas été trouvée", null);
            }
            if (teacherAccount == null) {
                hashMap.put("Le prof a été kidnappé, Gaël, libère le !", null);
            }
            teacherAccount.setTeacherSession((List<Session>) session);
            hashMap.put("Formation attribuée à l'apprenant", teacherAccountRepository.save(teacherAccount));
        }catch (Exception e) {
            hashMap.put("Erreur à cause de " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(hashMap);
        }
        return ResponseEntity.ok(hashMap);
    }
}
