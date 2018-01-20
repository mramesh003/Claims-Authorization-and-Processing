function getFirstDay(theYear, theMonth) {
	var firstDate = new Date(theYear, theMonth, 1)
	return firstDate.getDay()
}
// number of days in the month
function getMonthLen(theYear, theMonth) {
	var oneDay = 1000 * 60 * 60 * 24
	var thisMonth = new Date(theYear, theMonth, 1)
	var nextMonth = new Date(theYear, theMonth + 1, 1)
	var len = Math.ceil((nextMonth.getTime() - thisMonth.getTime()) / oneDay)
	return len
}
// create array of English month names
var theMonths = [ "January", "February", "March", "April", "May", "June",
		"July", "August", "September", "October", "November", "December" ]
// return IE4+ or W3C DOM reference for an ID
function getObject(obj) {
	var theObj
	if (document.all) {
		if (typeof obj == "string") {
			return document.all(obj)
		} else {
			return obj.style
		}
	}
	if (document.getElementById) {
		if (typeof obj == "string") {
			return document.getElementById(obj)
		} else {
			return obj.style
		}
	}
	return null
}

/*******************************************************************************
 * DRAW CALENDAR CONTENTS
 ******************************************************************************/
// clear and re-populate table based on form's selections
function populateTable(form) {
	var theMonth = form.chooseMonth.selectedIndex
	var theYear = parseInt(form.chooseYear.options[form.chooseYear.selectedIndex].text)

	var firstDay = getFirstDay(theYear, theMonth)
	var howMany = getMonthLen(theYear, theMonth)
	var secondHalfFirstDay = (firstDay + 1) % 7

	var secondYear;
	var secondMonth = (theMonth + 1) % 12;
	if (theMonth < 11)
		secondYear = theYear;
	else
		secondYear = theYear + 1;

	getObject("tableHeader").innerHTML = "16th " + theMonths[theMonth] + " " + theYear
			+ " - 15th " + theMonths[secondMonth] + " " + secondYear

	var TBody = getObject("tableBody")
	// clear any existing rows
	while (TBody.rows.length > 0) {
		TBody.deleteRow(0)
	}
	var newR, newC
	var done = false

	var j = 1;
	var dayCounter = 16;
	var counter = 16;
	while (!done) {
		// create new row at end
		newR = TBody.insertRow(TBody.rows.length)
		for (var i = 0; i < 7; i++) {
			// create new cell at end of row
			newC = newR.insertCell(newR.cells.length)
			if (TBody.rows.length == 1 && i < secondHalfFirstDay) {
				// no content for boxes before first day
				newC.innerHTML = ""
				continue
			}
			if (counter == howMany + 15) {
				// no more rows after this one
				done = true
			}
			if (dayCounter > howMany)
				dayCounter = 1

			if (counter <= howMany + 15) {
				var select = document.createElement("select");
				var s = "shiftdetail" + dayCounter;
				select.setAttribute("name", s);
				select.setAttribute("id", s);
				select.options.add(new Option("", "a", true, true));
				select.options.add(new Option("shift B", "b"));
				select.options.add(new Option("shift C", "c"));
				select.options.add(new Option("", "d"));
				select.options.add(new Option("", "e"));
				newC.innerHTML = dayCounter
				newC.appendChild(select);
				dayCounter++
				counter++
			} else {
				newC.innerHTML = "";
			}
		}

	}
	var employeeId = document.getElementById("employeeId").value;
	var length = 0;
	$
			.ajax({
				url : "getCalendarData.do",
				dataType : 'json',
				data : {
					employeeId : employeeId,
					startMonth : theMonth,
					startYear : theYear,
					endMonth : secondMonth,
					endYear : secondYear
				},
				type : "POST",
				success : function(data) {
					var holidayCount = 0;
					var j = 0;
					for (i = 1; i <= howMany; i++) {
						var s = "shiftdetail" + i;
						for (var j = 0; j < data.dayData.length; j++) {
							if (i == data.dayData[j].date) {
								if (data.dayData[j].isOnShoreHoliday)
								{
									holidayCount++;
									document.getElementById(s).value = 'd';
									$('#'+s).css("background-color","#13b4ff");
								}
								else if(data.dayData[j].isOffShoreHoliday)
								{
									holidayCount++;
									document.getElementById(s).value = 'e';
									$('#'+s).css("background-color","#ffff00");
								}
								else
									document.getElementById(s).value = data.dayData[j].shift;
							}
						}
					}
					if (data.dayData.length == 0 || data.dayData.length == holidayCount) {
						document.getElementById('submit').style.visibility = 'visible';
						document.getElementById('update').style.visibility = 'hidden';
					} else {
						document.getElementById('update').style.visibility = 'visible';
						document.getElementById('submit').style.visibility = 'hidden';
					}

				}
			});
}
function fillYears() {
	var today = new Date()
	var thisYear = today.getFullYear()
	var yearChooser = document.dateChooser.chooseYear
	for (i = thisYear; i < thisYear + 5; i++) {
		yearChooser.options[yearChooser.options.length] = new Option(i, i)
	}
	setCurrMonth(today)
}
// set month choice to current month
function setCurrMonth(today) {
	document.dateChooser.chooseMonth.selectedIndex = today.getMonth()
}