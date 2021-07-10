package com.gianvittorio.kafka.examples.ktableaggregate.services;

import com.gianvittorio.kafka.examples.model.DepartmentAggregate;
import com.gianvittorio.kafka.examples.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class RecordBuilder {


    public DepartmentAggregate init() {
        DepartmentAggregate departmentAggregate = new DepartmentAggregate();

        departmentAggregate.setEmployeeCount(0);
        departmentAggregate.setTotalSalary(0);
        departmentAggregate.setAvgSalary(0D);

        return departmentAggregate;
    }

    public DepartmentAggregate add(Employee employee, DepartmentAggregate departmentAggregate) {

        departmentAggregate.setEmployeeCount(departmentAggregate.getEmployeeCount() + 1);
        departmentAggregate.setTotalSalary(departmentAggregate.getTotalSalary() + employee.getSalary());

        double avgSalary = ((double) departmentAggregate.getTotalSalary()) / ((double) departmentAggregate.getEmployeeCount());
        departmentAggregate.setAvgSalary(avgSalary);

        return departmentAggregate;
    }

    public DepartmentAggregate subtract(Employee employee, DepartmentAggregate departmentAggregate) {

        departmentAggregate.setEmployeeCount(departmentAggregate.getEmployeeCount() - 1);
        departmentAggregate.setTotalSalary(departmentAggregate.getTotalSalary() - employee.getSalary());

        double avgSalary = ((double) departmentAggregate.getTotalSalary()) / ((double) departmentAggregate.getEmployeeCount());
        departmentAggregate.setAvgSalary(avgSalary);

        return departmentAggregate;
    }
}
