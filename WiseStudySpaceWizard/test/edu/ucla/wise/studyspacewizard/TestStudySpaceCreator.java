package edu.ucla.wise.studyspacewizard;

public class TestStudySpaceCreator {

	public static void main(String[] args){
		
		
		if(!StudySpaceCreator.createStudySpace("testingSSCreator", "test","")){
			System.out.println("Study space creator failed");;
		}
		
		
	}
	
}
