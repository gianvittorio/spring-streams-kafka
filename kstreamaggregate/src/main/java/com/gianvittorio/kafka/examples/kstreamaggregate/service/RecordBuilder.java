package com.gianvittorio.kafka.examples.kstreamaggregate.service;

import com.gianvittorio.kafka.examples.model.DepartmentAggregate;
import com.gianvittorio.kafka.examples.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class RecordBuilder {


    public DepartmentAggregate init() {
        DepartmentAggregate departmentAggregate = new DepartmentAggregate();

        departmentAggregate.setEmployeeCount(0);
        departmentAggregate.setTotalSalary(0);
        departmentAggregate.setAvgSalary(0.0);

        return departmentAggregate;
    }

    public DepartmentAggregate aggregate(Employee employee, DepartmentAggregate departmentAggregate) {

        departmentAggregate.setEmployeeCount(departmentAggregate.getEmployeeCount() + 1);
        departmentAggregate.setTotalSalary(departmentAggregate.getTotalSalary() + employee.getSalary());

        double avgSalary = ((double) departmentAggregate.getTotalSalary()) / ((double) departmentAggregate.getEmployeeCount());
        departmentAggregate.setAvgSalary(avgSalary);

        return departmentAggregate;
    }
}
