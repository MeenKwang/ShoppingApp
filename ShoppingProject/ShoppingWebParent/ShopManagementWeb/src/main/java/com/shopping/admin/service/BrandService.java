package com.shopping.admin.service;

import com.shopping.admin.dto.BrandFormDto;
import com.shopping.admin.dto.BrandListDto;
import com.shopping.admin.dto.BrandSelectDto;
import com.shopping.admin.exception.BrandNotFoundException;
import com.shopping.common.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BrandService {
    int page = 10;
    public Page<BrandListDto> listByPage(Integer pageNum, String sortField, String sortDir, String nameSearch, Integer pageSize);
    public Brand save(BrandFormDto dto);
    public BrandFormDto getBrandDtoById(Integer id) throws BrandNotFoundException;
    public Boolean delete(Integer id) throws BrandNotFoundException;
    public Brand get(Integer brandId) throws BrandNotFoundException;
    public List<BrandSelectDto> listBrandsUsedInForm();

}
