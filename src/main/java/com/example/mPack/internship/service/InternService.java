package com.example.mPack.internship.service;

import com.example.mPack.internship.dto.InternDto;
import com.example.mPack.internship.dto.ResponseCodeDto;
import com.example.mPack.internship.dto.ResponseDto;
import com.example.mPack.internship.entity.Intern;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface InternService  {

    ResponseDto createIntern (InternDto internDto);

    List<InternDto> getAllIntern ();

    InternDto getInternById (Long id);

    ResponseCodeDto updateIntern (InternDto internDto, Long id);

    ResponseCodeDto updateInternByPatch (InternDto internDto, Long id);

    ResponseCodeDto deleteIntern (Long id);

    InternDto getByEmail (String email);

    InternDto getByMObile(String mobile);

    List<InternDto> getByDesignation (String designation);

    List<InternDto>  getByDivision (String division);

    List<InternDto> getByDepartment (String department);

    List<ResponseDto> getByBloogGroup (String bloodGroup);

    List<InternDto> getByDepartmentDivision (String division, String department);


}
