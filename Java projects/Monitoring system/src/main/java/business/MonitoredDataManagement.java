package business;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import data.Data;

public class MonitoredDataManagement {

	private List<MonitoredData> list = new ArrayList<MonitoredData>();

	public MonitoredDataManagement() {
		list = Data.getMonitoredDataList();
	
	}

	public void printList() {
		list.forEach(
				e -> System.out.println(e.getStartTime() + "\t\t" + e.getEndTime() + "\t\t" + e.getActivityName()));
	}

	public String getNumberOfDays() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime start = LocalDateTime.parse(list.get(0).getStartTime(), formatter);
		LocalDateTime end = LocalDateTime.parse(list.get(list.size() - 1).getEndTime(), formatter);
		Long days = ChronoUnit.DAYS.between(start, end);
		return days.toString();
	}

	public List<MonitoredData> getList() {
		return list;
	}

	public Map<String, Long> countAppearances() {

		Map<String, Long> map = list.stream()
				.collect(Collectors.groupingBy(MonitoredData::getActivityName, Collectors.counting()));
		return map;
	}

	public Map<String, Map<String, Long>> countPerDays() {
		Map<String, Map<String, Long>> map = list.stream().collect(Collectors.groupingBy(MonitoredData::getActivityName,
				Collectors.groupingBy(MonitoredData::getDate, Collectors.counting())));
		return map;
	}

	public Map<String, Long> countDurationPerActivityTableView() {

		Map<String, Long> map = list.stream().collect(Collectors.groupingBy(MonitoredData::getActivityName,
				Collectors.summingLong(MonitoredData::getDuration)));

		return map;
	}
	
	public List<String> filter(){

		Map<String, Long> mapAppearances=countAppearances();
		Map<String, Long> mapDuration = list.stream().filter(f->f.getDuration()<5)
				.collect(Collectors.groupingBy(MonitoredData::getActivityName, Collectors.counting()));
		List<String> filterList=new ArrayList<String>();
		for(Map.Entry<String,Long> element: mapDuration .entrySet()) {
			Double percentage=0.0;
			
			percentage=(double) (element.getValue())/(double)mapAppearances.get(element.getKey());
			//System.out.println(element.getKey()+"\t\t"+percentage+"\t\t"+element.getValue());
			if(percentage>0.9) {
				filterList.add(element.getKey());
			}
		}
		return filterList;
		
		
	}

}
