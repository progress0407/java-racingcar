package racingcar.view;

import static java.lang.System.*;

import racingcar.domain.Car;

import java.util.List;
import java.util.stream.Collectors;

public class OutputView {

	private static final String DECLARE_WINNER_MESSAGE = "가 최종 우승했습니다.";
	private static final String EXECUTION_RESULT_MESSAGE = "실행 결과";
	public static final String CAR_NAME_DELIMINATOR = ",";

	public static void showGameResult(final List<Car> winners) {
		final String winnerNames = winners.stream()
			.sorted(Car::compareNameTo)
			.map(Car::getName)
			.collect(Collectors.joining(CAR_NAME_DELIMINATOR));

		out.println(winnerNames + DECLARE_WINNER_MESSAGE);
	}

	public static void showCurrentStatus(final List<Car> cars) {
		for (Car car : cars) {
			out.println(car);
		}
		out.println();
	}

	public static void printResultMessage() {
		out.println(EXECUTION_RESULT_MESSAGE);
	}
}
