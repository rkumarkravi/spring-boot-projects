package com.rk.hrm.absence.controllers;

import com.rk.hrm.absence.dtos.AbsenceDto;
import com.rk.hrm.absence.exceptions.AbsenceExistException;
import com.rk.hrm.absence.exceptions.ResourceNotFoundException;
import com.rk.hrm.absence.mappers.AbsenceMapper;
import com.rk.hrm.absence.services.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/absence")
public class AbsenceController {
    @Autowired
    AbsenceService absenceService;
    @Autowired
    AbsenceMapper absenceMapper;

    @PostMapping(value = "/attendance/{uid}")
    private AbsenceDto addAttendance(@PathVariable("uid") long uid) throws AbsenceExistException {
        return absenceMapper.absenceToAbsenceDto(this.absenceService.addAttendance(uid));
    }

    @GetMapping(value = "/attendance/{uid}/{month}")
    private Set<Map<String, String>> getAttendance(@PathVariable("uid") long uid, @PathVariable("month") Integer month) throws AbsenceExistException, ParseException {
        return this.absenceService.getAttendanceByMonth(uid, month);
    }

    @PostMapping(value = "/{uid}")
    private AbsenceDto addAbsence(@PathVariable("uid") long uid, @RequestBody Map<String, String> body) throws AbsenceExistException, ParseException {
        return this.absenceMapper.absenceToAbsenceDto(this.absenceService.addAbsence(uid, body));
    }

    @DeleteMapping("/{id}")
    private Integer withdrawAbsence(@PathVariable("id") Long id) throws ResourceNotFoundException {
        this.absenceService.withdrawAbsence(id);
        return 1;
    }

}
