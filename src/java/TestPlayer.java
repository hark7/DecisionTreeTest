import com.knowlegevalues.assignment.model.Result;
import com.knowlegevalues.assignment.service.Player;

/**
 * This class uses a normal java main class to invoke 
 * one among the different test scenarios
 * For testing a scenario, just use the test method name inside main function
 * @author harikrishnankesavan
 *
 */
		
public class TestPlayer {
	
	public static void main(String [] args) {
		test2();
	}
	
	
	private static Result test1() {
		Result result = null;
		Player player = new Player();
		String selectedNode = null;
		do {
			result = player.consult("Start.xml", selectedNode, "Decrease by 5 degrees");
			selectedNode = result.getSelectedNode();
		} while (!result.isLeaf());
		return result;
	}
	
	private static Result test2() {
		Result result = null;
		Player player = new Player();
		result = player.consult("Start.xml", null, null);
		System.out.println("\nOptions to select are:");
		for(String name:result.getChildNodeNames()) {
			System.out.println("- "+name);
		}
		return result;
	}
	
	private static Result test3() {
		Result result = null;
		Player player = new Player();
		do {
			result = player.consult("Start.xml", "Decrease", "Decrease by 5 degrees");
		} while (!result.isLeaf());
		return result;
	}
	
	private static Result test4() {
		Result result = null;
		Player player = new Player();
		result = player.consult("Start.xml", "Decrease", null);
		System.out.println("\nOptions to select are:");
		for(String name:result.getChildNodeNames()) {
			System.out.println("- "+name);
		}
		return result;
	}

}
