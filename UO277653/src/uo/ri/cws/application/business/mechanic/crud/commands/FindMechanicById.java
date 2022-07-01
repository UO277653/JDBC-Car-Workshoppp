package uo.ri.cws.application.business.mechanic.crud.commands;

import java.util.Optional;

import alb.util.assertion.Argument;
import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

public class FindMechanicById implements Command<Optional<MechanicDto>>{

	private MechanicGateway mg = PersistenceFactory.forMechanic();
	private String idMechanic;
	
	public FindMechanicById(String arg) {
		
		Argument.isNotNull(arg);
		Argument.isTrue(!arg.trim().isEmpty());
		
		idMechanic = arg;
	}

	public Optional<MechanicDto> execute() {

		return DtoAssembler.toDto(mg.findById(idMechanic));
		
	}
	
}
