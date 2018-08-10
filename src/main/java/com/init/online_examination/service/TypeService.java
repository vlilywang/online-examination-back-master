package com.init.online_examination.service;

import com.init.online_examination.domain.Type;
import com.init.online_examination.domain.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
    private TypeRepository typeRepository;

    @Autowired
    public void setTypeRepository(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public Type createType(String typeName) {
        Type type = new Type();
        type.setTypeName(typeName);
        typeRepository.save(type);
        return type;
    }

    public Long countTypes() {
        return typeRepository.count();
    }

    public Type findById(Long id) {
        return typeRepository.findFirstById(id);
    }
    public List<Type> findAll() {
        return typeRepository.findAll();
    }
}
