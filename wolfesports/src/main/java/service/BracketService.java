package service;

import repository.interfaces.BracketRepository;

public class BracketService {
    
    BracketRepository bracketRepository;

    public BracketService(BracketRepository bracketRepository){
        this.bracketRepository = bracketRepository;
    }
}
