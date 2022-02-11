package racingcar.controller;

import racingcar.domain.strategy.MovingStrategy;
import racingcar.domain.strategy.RandomMovingStrategy;
import racingcar.repository.CarRepository;
import racingcar.domain.Car;
import racingcar.validator.CarNameValidator;
import racingcar.view.*;

import java.util.*;
import java.util.stream.Collectors;

public class CarController {

	private static final String NOT_FOUND_CARS_MESSAGE = "[ERROR] 자동차를 찾을 수 없습니다.";
	private static final String CAR_NAME_DELIMINATOR = ",";

	private final CarRepository carRepository;
	private int roundNumber;
	private final MovingStrategy movingStrategy;

	public void playGame() {
		OutputView.printResultMessage();
		for (int i = 0; i < roundNumber; i++) {
			playRound();
		}
	}

	public CarController(CarRepository carRepository, MovingStrategy movingStrategy) {
		this.carRepository = carRepository;
		this.movingStrategy = movingStrategy;
	}

	public void playRound() {
		moveCars();
		List<Car> cars = carRepository.findAll();
		OutputView.showCurrentStatus(cars);
	}

	public void moveCars() {
		Random random = new Random();
		for (Car car : carRepository.findAll()) {
			car.move();
		}
	}

	public void initGame() {
		initCars();
		initRoundNumbers();
	}

	private void initCars() {
		String input = InputView.inputCarNames();

		CarNameValidator.parseCarNameInputs(input);

		String[] strings = parseCarNames(input);

		List<String> carNames = List.of(strings);
		List<Car> cars = new ArrayList<>();

		for (String carName : carNames) {
			Car car = new Car(carName, movingStrategy);
			cars.add(car);
		}

		carRepository.addCars(cars);
	}

	private String[] parseCarNames(String input) {
		input = input.replaceAll(" ", "");
		return input.split(CAR_NAME_DELIMINATOR);
	}

	private void initRoundNumbers() {
		roundNumber = InputView.inputRoundNumber();
	}

	public List<Car> getWinners() {
		List<Car> cars = carRepository.findAll();

		Car maxCar = cars.stream()
			.max(Car::compareTo)
			.stream()
			.findAny()
			.orElseThrow(() -> new RuntimeException(NOT_FOUND_CARS_MESSAGE));

		List<Car> winners = cars.stream()
			.filter(car -> car.isSamePosition(maxCar))
			.collect(Collectors.toList());

		return winners;
	}

	public void turnOffGame() {
		InputView.terminate();
		OutputView.showGameResult(getWinners());
	}
}
