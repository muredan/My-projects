package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import business.MonitoredData;

public class Data {

	public static List<MonitoredData> getMonitoredDataList() {
		List<MonitoredData> list = new ArrayList<MonitoredData>();
		try {
			Stream<String> stream = Files.lines(Paths.get("Activities.txt"));
			list = stream.map(line -> line.split("\t\t")).map(a -> new MonitoredData(a[0], a[1], a[2]))
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
