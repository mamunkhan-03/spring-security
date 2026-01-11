package com.example.mPack.internship.service.serviceImpl;

import com.example.mPack.internship.dto.InternDto;
import com.example.mPack.internship.dto.ResponseCodeDto;
import com.example.mPack.internship.dto.ResponseDto;
import com.example.mPack.internship.entity.Intern;
import com.example.mPack.internship.repository.InternRepository;
import com.example.mPack.internship.service.InternService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InternServiceImpl implements InternService {

    private InternRepository internRepository;
    private ModelMapper mapper;

    public InternServiceImpl(InternRepository internRepository, ModelMapper mapper) {
        this.internRepository = internRepository;
        this.mapper = mapper;
    }


    @Override
    public ResponseDto createIntern(InternDto internDto) {

        Intern createIntern=internRepository.save(mapToEntity(internDto));
        return new ResponseDto();
    }

    @Override
    public List<InternDto> getAllIntern() {
        return null;
    }

    @Override
    public InternDto getInternById(Long id) {
        return null;
    }

    @Override
    public ResponseCodeDto updateIntern(InternDto internDto, Long id) {
        return null;
    }

    @Override
    public ResponseCodeDto updateInternByPatch(InternDto internDto, Long id) {
        return null;
    }

    @Override
    public ResponseCodeDto deleteIntern(Long id) {
        return null;
    }

    @Override
    public InternDto getByEmail(String email) {
        return null;
    }

    @Override
    public InternDto getByMObile(String mobile) {
        return null;
    }

    @Override
    public List<InternDto> getByDesignation(String designation) {
        return null;
    }

    @Override
    public List<InternDto> getByDivision(String division) {
        return null;
    }

    @Override
    public List<InternDto> getByDepartment(String department) {
        return null;
    }

    @Override
    public List<ResponseDto> getByBloogGroup(String bloodGroup) {
        return null;
    }

    @Override
    public List<InternDto> getByDepartmentDivision(String division, String department) {
        return null;
    }

    //Convert entity into dto
    InternDto mapToDto (Intern intern){
        return mapper.map(intern, InternDto.class);
    }

    // Convert dto into entity
    Intern mapToEntity (InternDto internDto){
        return mapper.map(internDto, Intern.class);
    }
}
