package fr.labri.starnet.policies.commons.actions;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Map;

import fr.labri.starnet.INode;
import fr.labri.starnet.Message;
import fr.labri.starnet.policies.commons.DataSet;
import fr.labri.starnet.policies.commons.HelloSet;
import fr.labri.timedautomata.ITimedAutomata;
import fr.labri.timedautomata.TimedAutomata.StateAdapter;

public class DataActions {

	public static class InitEnv extends StateAdapter<INode> {
		public Map<String, Object> storage;

		@Override
		public void preAction(INode context, ITimedAutomata<INode> auto) {
			storage = context.getStorage();
			storage.put(CommonVar.DATA_SET, new DataSet());
			storage.put(CommonVar.HELLO_SET, new HelloSet());
			storage.put(CommonVar.SAVED_MAILBOX, new ArrayDeque<Message>());
		}
	}

	public static class SaveMailBox extends StateAdapter<INode> {
		public Map<String, Object> storage;

		@Override
		public void postAction(INode context, ITimedAutomata<INode> auto) {
			storage = context.getStorage();
			Message[] r = context.receive();
			Deque<Message> stack = new ArrayDeque<Message>(Arrays.asList(r));
			storage.put(CommonVar.SAVED_MAILBOX, stack);
		}
	}

	public static class AddToDataSet extends StateAdapter<INode> {
		Map<String, Object> storage;
		HelloSet data_set;

		@Override
		public void postAction(INode context, ITimedAutomata<INode> auto) {
			storage = context.getStorage();
			Message msg = (Message) storage
					.get(CommonVar.CURRENT_MESSAGE);
			data_set = (HelloSet) storage.get(CommonVar.DATA_SET);
			data_set.add(msg);
		}
	}
}