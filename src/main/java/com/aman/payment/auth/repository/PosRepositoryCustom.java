package com.aman.payment.auth.repository;

import java.util.List;

import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.model.User;

public interface PosRepositoryCustom {
    public List<Pos> getAllPos(long id);
    
    public List<User> getAllUsers(long id);
}
