package service;

import model.Warning;
import repository.interfaces.WarningRepository;

public class WarningService {

    WarningRepository warningRepository;

    public WarningService(WarningRepository warningRepository){
        this.warningRepository = warningRepository;
    }

	public void addWarning(Warning warning) {
		warningRepository.save(warning);
	}
}
