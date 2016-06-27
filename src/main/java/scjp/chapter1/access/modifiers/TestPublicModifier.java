package scjp.chapter1.access.modifiers;

/**
 * This class is written for learning access modifiers.
 * @author Sudarshan Suresh
 *
 */
public class TestPublicModifier implements SimpleInterface {

	private String name = null;

	public static void main(String[] args) {
		System.out.println(age);

		System.out.println(SimpleInterface.name);

		TestPublicModifier testPublicModifier = new TestPublicModifier();
		testPublicModifier.setName("Amos");

		System.out.println(testPublicModifier.getName());
	}

	public void setName(String s) {
		this.name = s;

	}

	public String getName() {

		return name;
	}

}
