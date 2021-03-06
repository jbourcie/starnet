package fr.labri.timedautomata;

import java.io.IOException;


import org.jdom2.JDOMException;

import fr.labri.timedautomata.TimedAutomata;
import fr.labri.timedautomata.ITimedAutomata.NodeFactory;
import fr.labri.timedautomata.ITimedAutomata.Action;
import fr.labri.timedautomata.ITimedAutomata.Predicate;
import fr.labri.timedautomata.TimedAutomata.NamedAction;
import fr.labri.timedautomata.TimedAutomata.TransitionAdapter;

public class TestTA {
	public static void main(String[] args) throws JDOMException, IOException {
		
		TimedAutomata<Object> b = TimedAutomata.getTimedAutoma(null, getSimpleNodeBuilder());
		b.loadXML(TestTA.class.getResourceAsStream("ex1.xml"), false);
		//DotViewer.view(b.toDot("ex1"));
		System.out.println(b.toString());
		AutomataViewer.viewAsFrame(b);
		//DotViewer.view(b.compile().toDot("ex1"));
		
		b = TimedAutomata.getTimedAutoma(null, getSimpleNodeBuilder());
		b.loadXML(TestTA.class.getResourceAsStream("ex2.xml"), false);
		//DotViewer.view(b.toDot("ex2"));
		System.out.println(b.toString());
		AutomataViewer.viewAsFrame(b);

		//DotViewer.view(b.compile().toDot("ex2"));
	}
	
	static <C> NodeFactory<C> getSimpleNodeBuilder() {
		return new NodeFactory<C>() {
			public Action<C> newState(final String name, final String type) {
				return new NamedAction<C>(name, null) {
					public String getType() {
						return name;
					}
				};
			}
			
			public Predicate<C> newPredicate(final String name) {
				return new TransitionAdapter<C>() {
					public String getType() {
						return name;
					}
				};
			}
		};
	}
}
