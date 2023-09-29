package com.servicehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servicehub.model.Issue;

public interface IssueRepository extends JpaRepository<Issue, Integer>{


}