package com.rk.hrm.services;

import com.rk.hrm.enums.AbsenceType;
import com.rk.hrm.enums.Status;
import com.rk.hrm.exceptions.AbsenceExistException;
import com.rk.hrm.exceptions.ResourceNotFoundException;
import com.rk.hrm.models.Absence;
import com.rk.hrm.repos.AbsenceRepository;
import com.rk.hrm.repos.UserRepository;
import com.rk.hrm.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        log.info("From {}, To {}", todayDate, todayDate);
        if (!absenceRepository.findByUser_IdAndFromAndTo(uid, todayDate, todayDate).isPresent()) {
            absence = new Absence();
            absence.setAbsenceType(AbsenceType.ATTENDANCE);
            absence.setFromDate(todayDate);
            absence.setToDate(todayDate);
            absence.setLeaveReason("SYSTEM_ADDED");
            absence.setStatus(Status.COMPLETED);
            absence.setUser(userRepository.findById(uid).get());
        } else {
            throw new AbsenceExistException("Absence Already Exists!");
        }

        return absenceRepository.save(absence);
    }

    public Absence addAbsence(Long uid, Map<String, String> map) throws ParseException, AbsenceExistException {
        Absence absence = null;
        Date fromDate = utils.parseDateWithFormat(map.get("fromDate"), "dd/MM/yyyy");
        Date toDate = utils.parseDateWithFormat(map.get("toDate"), "dd/MM/yyyy");
        AbsenceType absenceType = AbsenceType.valueOf(map.get("absenceType"));
        log.info("From {}, To {}", fromDate, toDate);
        if (!absenceRepository.findByUser_IdAndFromAndTo(uid, fromDate, toDate).isPresent()) {
            absence = new Absence();
            absence.setAbsenceType(absenceType);
            absence.setFromDate(fromDate);
            absence.setToDate(toDate);
            absence.setStatus(Status.PENDING);
            absence.setLeaveReason(map.get("reason"));
            absence.setUser(userRepository.findById(uid).get());
        } else {
            throw new AbsenceExistException("Absence Already Exists!");
        }

        return absenceRepository.save(absence);
    }

    public Integer withdrawAbsence(Long absenceId) throws ResourceNotFoundException {
        if (this.absenceRepository.existsById(absenceId)) {
            absenceRepository.delete(absenceRepository.getReferenceById(absenceId));
        } else {
            throw new ResourceNotFoundException("Absence ID not available!");
        }
        return 1;
    }

    public Set<Map<String, String>> getAttendanceByMonth(Long uid, int month) throws ParseException {
        Calendar c = Calendar.getInstance();
        YearMonth yearMonth = YearMonth.of(c.get(Calendar.YEAR), month);
        log.info("month is {} ,len month {}", yearMonth.getMonth().getValue(), yearMonth.lengthOfMonth());
        Date dFrom = this.utils.parseDateWithFormat("01" + "/" + yearMonth.getMonth().getValue() + "/" + yearMonth.getYear(), "dd/MM/yyyy");
        Date dTo = this.utils.parseDateWithFormat(yearMonth.lengthOfMonth() + "/" + yearMonth.getMonth().getValue() + "/" + yearMonth.getYear(), "dd/MM/yyyy");
        log.info("From {}, To {}", dFrom, dTo);
        return this.absenceRepository.findByUser_IdAndFromDateBetween(uid, dFrom, dTo).stream().map((x) -> {
            Map<String, String> m = new HashMap<>();
            m.put("date", String.valueOf(x.getFromDate()));
            m.put("type", x.getAbsenceType().toString());
            m.put("id", x.getId().toString());
            return m;
        }).collect(Collectors.toSet());
    }
}
