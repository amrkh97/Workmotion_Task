package com.workmotion.employee.StateMachine;

import com.github.oxo42.stateless4j.StateMachineConfig;
import com.workmotion.employee.Models.EmployeeEvent;
import com.workmotion.employee.Models.EmployeeState;

public class EmployeeStateConfig{
    public static StateMachineConfig<EmployeeState, EmployeeEvent> build(){

        StateMachineConfig<EmployeeState, EmployeeEvent> config = new StateMachineConfig<>();

        config.configure(EmployeeState.ADDED)
                .permit(EmployeeEvent.BEGIN_CHECK,EmployeeState.IN_CHECK);

        config.configure(EmployeeState.IN_CHECK)
                .permit(EmployeeEvent.APPROVE,EmployeeState.APPROVED);

        config.configure(EmployeeState.APPROVED)
                .permit(EmployeeEvent.ACTIVATE,EmployeeState.ACTIVE);

        config.configure(EmployeeState.APPROVED)
                .permit(EmployeeEvent.UNAPPROVE,EmployeeState.IN_CHECK);

        return config;

    }
}


