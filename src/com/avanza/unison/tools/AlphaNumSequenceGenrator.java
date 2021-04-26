package com.avanza.unison.tools;

import com.avanza.unison.tools.objects.Sequence;

public class AlphaNumSequenceGenrator implements  ISequenceGenerator {

	private Sequence start = new Sequence();

	private String numericTemplate= "";
	
	public AlphaNumSequenceGenrator(Sequence metaType) {
		start = metaType;
		String template = start.getTemplate();
		numericTemplate = Util.ExtractDigit(template);
	}

	
	public String Generate() {
		
		long initialize = start.getStart();
		long newSequence = initialize + start.getIncrement();
		start.setStart(newSequence);
		String returnValue = String.format("%1$" + numericTemplate.length() + "s", newSequence).replace(' ', '0');
		returnValue = start.getTemplate().replace(numericTemplate, returnValue);
		return returnValue;

	}

}
