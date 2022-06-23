package com.rk.hrm.services;

import com.rk.hrm.enums.AbsenceType;
import com.rk.hrm.enums.Status;
import com.rk.hrm.exceptions.AbsenceExistException;
import com.rk.hrm.models.Absence;
import com.rk.hrm.repos.AbsenceRepository;
import com.rk.hrm.repos.UserRepository;
import com.rk.hrm.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AbsenceService {
    @Autowired
    AbsenceRepository absenceRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    public Absence addAttendance(long uid) throws AbsenceExistException {
        Date todayDate = new Date();
        Absence absence = null;

        if (!absenceRepository.findByUser_IdAndFromAndTo(uid, todayDate, todayDate).isPresent()) {
            absence = new Absence();
            absence.setAbsenceType(AbsenceType.ATTENDANCE);
            absence.setFrom(todayDate);
            absence.setTo(todayDate);
            absence.setLeaveReason("SYSTEM_ADDED");
            absence.setStatus(Status.COMPLETED);
            absence.setUser(userRepository.findById(uid).get());
        } else {
            throw new AbsenceExistException("Absence Already Exists!");
        }

        return absenceRepository.save(absence);
    }

    private Absence addAbsence(Long uid, Map<String, String> map) throws ParseException, AbsenceExistException {
        Absence absence = null;
        Date fromDate = utils.parseDateWithFormat(map.get("fromDate"), "DD/MM/YYYY");
        Date toDate = utils.parseDateWithFormat(map.get("toDate"), "DD/MM/YYYY");
        AbsenceType absenceType = AbsenceType.valueOf(map.get("absenceType"));
        if (!absenceRepository.findByUser_IdAndFromAndTo(uid, fromDate, toDate).isPresent()) {
            absence = new Absence();
            absence.setAbsenceType(absenceType);
            absence.setFrom(fromDate);
            absence.setTo(toDate);
            absence.setStatus(Status.PENDING);
            absence.setLeaveReason(map.get("reason"));
            absence.setUser(userRepository.findById(uid).get());
        } else {
            throw new AbsenceExistException("Absence Already Exists!");
        }

        return absenceRepository.save(absence);
    }

    private Integer withdrawAbsence(Long absenceId) {
        absenceRepository.delete(absenceRepository.getReferenceById(absenceId));
        return 1;
    }

    private Set<Map<String, String>> getAttendanceByMonth(int month) throws ParseException {
        Calendar c = Calendar.getInstance();
        YearMonth yearMonth = YearMonth.of(c.get(Calendar.YEAR), month);
        Date dFrom = this.utils.parseDateWithFormat("01" + "/" + yearMonth.getMonth() + "/" + yearMonth.getYear(), "DD/MM/YYYY");
        Date dTo = this.utils.parseDateWithFormat(yearMonth.lengthOfMonth() + "/" + yearMonth.getMonth() + "/" + yearMonth.getYear(), "DD/MM/YYYY");
        return this.absenceRepository.findByFromBetween(dFrom, dTo).stream().map((x) -> {
            Map<String, String> m = new HashMap<String, String>();
            m.put("date", String.valueOf(x.getFrom().getTime()));
            m.put("type", x.getAbsenceType().toString());
            return m;
        }).collect(Collectors.toSet());
    }
}
