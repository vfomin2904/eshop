package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductDao;
import ru.geekbrains.persist.Role;
import ru.geekbrains.persist.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RolesService {

    private RoleRepository roleRepository;

    @Autowired
    public RolesService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

   public List<Role> findAll(){return roleRepository.findAll();}

   public Optional<Role> findById(Long id){
        return roleRepository.findById(id);
   }

   public void save(Role role){
       roleRepository.save(role);
   }

    public void deleteById(Long id){
        roleRepository.deleteById(id);
    }
}
