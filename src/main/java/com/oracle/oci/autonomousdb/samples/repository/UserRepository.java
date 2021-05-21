package com.oracle.oci.autonomousdb.samples.repository;

import java.util.UUID;

import com.oracle.oci.autonomousdb.samples.model.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author bnasslahsen
 */
public interface UserRepository extends JpaRepository<UserEntity, UUID> {


}
