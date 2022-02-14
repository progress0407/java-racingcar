package racingcar;

import racingcar.controller.CarController;
import racingcar.repository.CarRepository;
import racingcar.view.InputView;

public class RacingCarApplication {

	private static CarController carController;
	private static CarRepository carRepository;
	private static InputView inputView;

	public static void main(String[] args) {

		injectDependency();
		playGame();
	}

	private static void playGame() {
		carController.initGame();
		carController.playGame();
		carController.showWinners();
		carController.end();
	}

	private static void injectDependency() {
		inputView = new InputView();
		carRepository = new CarRepository();
		carController = new CarController(carRepository, inputView);
	}
}
