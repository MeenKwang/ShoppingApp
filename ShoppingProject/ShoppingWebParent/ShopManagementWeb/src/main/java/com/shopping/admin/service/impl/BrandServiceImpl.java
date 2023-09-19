package com.shopping.admin.service.impl;

import com.shopping.admin.dto.BrandFormDto;
import com.shopping.admin.dto.BrandListDto;
import com.shopping.admin.dto.BrandSelectDto;
import com.shopping.admin.dto.UserDTO;
import com.shopping.admin.dto.mapper.BrandFormMapper;
import com.shopping.admin.dto.mapper.BrandListMapper;
import com.shopping.admin.exception.BrandNotFoundException;
import com.shopping.admin.repository.BrandRepository;
import com.shopping.admin.service.BrandService;
import com.shopping.common.entity.Brand;
import com.shopping.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;
@Service
@Transactional
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandListMapper brandListMapper;
    private final BrandFormMapper brandFormMapper;
    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository, BrandListMapper brandListMapper, BrandFormMapper brandFormMapper) {
        this.brandRepository = brandRepository;
        this.brandListMapper = brandListMapper;
        this.brandFormMapper = brandFormMapper;
    }
    @Override
    public Page<BrandListDto> listByPage(Integer pageNum, String sortField, String sortDir, String nameSearch, Integer pageSize) {
        Sort sort = Sort.by(sortField);
        if(sortDir.equals("asc")) {
            sort = sort.ascending();
        } else {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<Brand> brandPage = brandRepository.findAll(pageable, nameSearch);
        Page<BrandListDto> brandListDtoPage = brandPage.map(
                new Function<Brand, BrandListDto>() {
                    @Override
                    public BrandListDto apply(Brand brand) {
                        if(brand == null) return null;
                        return brandListMapper.brandToBrandListDto(brand);
                    }
                }
        );
        return brandListDtoPage;
    }

    @Override
    public Brand save(BrandFormDto dto) {
        try {
            Brand brand = brandFormMapper.brandFormDtoToBrand(dto);
            Brand savedBrand = brandRepository.save(brand);
            return savedBrand;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public BrandFormDto getBrandDtoById(Integer id) throws BrandNotFoundException {
        try{
            Brand brand = brandRepository.findById(id).orElse(null);
            if(brand != null) {
                BrandFormDto dto = brandFormMapper.brandToBrandFormDto(brand);
                return dto;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new BrandNotFoundException("Could not find any brand with ID: " + id);
        }
    }

    @Override
    public Boolean delete(Integer id) throws BrandNotFoundException {
        try{
            brandRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new BrandNotFoundException("Could not find any brand with ID: " + id);
        }
    }

    @Override
    public Brand get(Integer brandId) throws BrandNotFoundException {
        try {
            return brandRepository.findById(brandId).get();
        } catch (NoSuchElementException e) {
            throw new BrandNotFoundException("Could not find any brand with ID: " + brandId);
        }
    }

    @Override
    public List<BrandSelectDto> listBrandsUsedInForm() {
        List<BrandSelectDto> brandSelectDtoList = brandRepository.findAllWithCustomObject();
        return brandSelectDtoList;
    }
}
