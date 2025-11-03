<%@ page import="java.util.*"%>
<%
List<String> tips = (List<String>) request.getAttribute("tips");
if (tips == null || tips.isEmpty()) {
	tips = Arrays.asList("Use public transport instead of private vehicles.", "Turn off lights when not needed.",
	"Plant more trees to offset CO2.", "Unplug devices when not in use.",
	"Support local products to reduce shipping emissions.");
}
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carbon Emission Dashboard</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f3f4f6;
            margin: 0;
            padding: 0;
        }
        .container {
            display: flex;
            min-height: 100vh;
        }
        .sidebar {
            background-color: #065f46;
            color: white;
            width: 250px;
            padding: 20px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }
        .sidebar img {
            border-radius: 50%;
            width: 50px;
            height: 50px;
        }
        .sidebar .profile {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
        }
        .sidebar .profile span {
            margin-left: 10px;
            font-size: 1.25rem;
        }
        .sidebar nav ul {
            list-style: none;
            padding: 0;
        }
        .sidebar nav ul li {
            margin-bottom: 20px;
        }
        .sidebar nav ul li a {
            color: white;
            text-decoration: none;
            display: flex;
            align-items: center;
        }
        .sidebar nav ul li a:hover {
            color: #d1d5db;
        }
        .sidebar nav ul li a i {
            margin-right: 10px;
        }
        .sidebar button {
            background-color: #ef4444;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            display: flex;
            align-items: center;
            cursor: pointer;
        }
        .sidebar button:hover {
            background-color: #dc2626;
        }
        .sidebar button i {
            margin-right: 10px;
        }
        .main-content {
            flex: 1;
            padding: 20px;
            position: relative;
        }
        .welcome {
            background-color: #065f46;
            color: white;
            padding: 20px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .cards {
            display: grid;
            grid-template-columns: 1fr;
            gap: 20px;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 100%;
            max-width: 800px;
            z-index: 1;
        }
        @media (min-width: 768px) {
            .cards {
                grid-template-columns: repeat(3, 1fr);
            }
        }
        .card {
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            text-align: center;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .card i {
            font-size: 2rem;
            color: #065f46;
            margin-bottom: 10px;
        }
        .card .value {
            font-size: 1.5rem;
            font-weight: bold;
        }
        .card .label {
            color: #6b7280;
        }
        .image-container {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
            z-index: 0;
        }
        .image-container img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            filter: blur(8px);
        }
        .form-container {
            display: none;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .form-container input, .form-container select, .form-container button {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .result-container {
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            margin: 0 auto;
            max-width: 600px;
            position: relative;
            z-index: 2;
        }
        .result-container h2 {
            color: #065f46;
            margin-top: 0;
        }
        .result-container p {
            margin: 10px 0;
        }
        .result-container p strong {
            color: #065f46;
            font-size: 1.2rem;
        }
    </style>
</head>
<body onload="${emission != null ? 'showResult()' : ''}">
    <div class="container">
        <!-- Sidebar -->
        <div class="sidebar">
            <div>
                <div class="profile">
                    <img src="https://storage.googleapis.com/a1aa/image/1oz8d9XlBlFws9zEL_ym8ZEitL9Bpid8FbQAKMOjCoU.jpg" alt="User profile picture">
                    <span>${username}</span>
                </div>
                <nav>
                    <ul class="space-y-6">
                        <li>
                            <a href="#" onclick="showDashboard()"><i class="fas fa-chart-line"></i> Dashboard</a>
                        </li>
                        <li>
                            <a href="#" onclick="showForm()"><i class="fas fa-map-marker-alt"></i> Regions & Locations</a>
                        </li>
                        
                        <li>
                            <a href="#" onclick="showReport()"><i class="fas fa-file-alt"></i> Reports</a>
                        </li>
                    </ul>
                </nav>
            </div>
            <div>
                <form action="LogoutServlet" method="get">
                    <button type="submit"><i class="fas fa-sign-out-alt"></i> Logout</button>
                </form>
            </div>
        </div>
        <!-- Main Content -->
        <div class="main-content">
            <div class="welcome">
                <h1>Welcome, ${username}</h1>
            </div>
            <div class="image-container" id="image-container">
                <img src="images/dashboard1.png" alt="Factory pollution illustration with smoke coming out of chimneys">
            </div>
            <div class="cards" id="cards">
                <div class="card">
                    <i class="fas fa-cloud"></i>
                    <div class="value">1,250 t CO<sub>2</sub></div>
                    <div class="label">Total Emissions</div>
                </div>
                <div class="card">
                    <i class="fas fa-map-marker-alt"></i>
                    <div class="value">8</div>
                    <div class="label">Locations Tracked</div>
                </div>
                <div class="card">
                    <i class="fas fa-chart-line"></i>
                    <div class="value">Emissions Trend</div>
                    <div class="label">View Graph</div>
                </div>
            </div>
            <div class="form-container" id="form-container">
                <h2>Regions & Locations</h2>
                <form id="carbonEmissionForm" action="RegionSelection" method="post">
                    <!-- Region -->
                    <div class="form-group">
                        <label for="region">Region</label>
                        <select id="region" name="region">
                            <option value="garden">Garden</option>
                            <option value="industry">Industry</option>
                            <option value="home">Home</option>
                            <option value="office">Office</option>
                            <option value="roadside">Roadside</option>
                            <option value="market">Market</option>
                             <option value="classroom">classroom</option>
                              <option value="concert">concert</option>
                               <option value="cafeteria">cafeteria</option>
                                <option value="pub">pub</option>
                                 <option value="conferencehall">conference hall</option>
                                  <option value="mall">mall</option>
                                   <option value="restaurant">restaurant</option>
                                    <option value="kitchen">kitchen</option>
                        </select>
                    </div>
                    
                    <!-- Purpose of Visit -->
                    <div class="form-group">
                        <label for="purpose">Purpose of Visit</label>
                        <select id="purpose" name="purpose">
                            <option value="walk">Walk</option>
                            <option value="work">Work</option>
                            <option value="passing-by">Passing By</option>
                            <option value="relax">Relax</option>
                            <option value="shopping">Shopping</option>
                        </select>
                    </div>
                    
                    <!-- Number of People -->
                    <div class="form-group">
                        <label for="people">Number of People</label>
                        <input type="number" name="people" id="people" min="0" required />
                    </div>
                    
                    <!-- Devices Turned On -->
                    <!-- Devices Turned On -->
					<div class="form-group">
					    <label for="devices">Devices Turned On</label>
					    <div id="devices-container">
					        <!-- Devices checkbox will be dynamically populated here -->
					    </div>
					</div>

                    
                    <!-- Are You Using Any Vehicle? -->
                    <div class="form-group">
                        <label for="vehicle">Are You Using Any Vehicle?</label>
                        <select id="vehicle" name="vehicle">
                            <option value="bike">Bike</option>
                            <option value="car">Car</option>
                            <option value="public-transport">Public Transport</option>
                            <option value="none">None</option>
                        </select>
                    </div>
                    
                    <!-- Traffic Nearby -->
                    <div class="form-group">
                        <label for="traffic">Traffic Nearby</label>
                        <select id="traffic" name="traffic">
                            <option value="none">None</option>
                            <option value="light">Light</option>
                            <option value="moderate">Moderate</option>
                            <option value="heavy">Heavy</option>
                        </select>
                    </div>
                    
                    <!-- Ventilation Level -->
                    <div class="form-group">
                        <label for="ventilation">Ventilation Level</label>
                        <select id="ventilation" name="ventilation">
                            <option value="low">Low</option>
                            <option value="moderate">Moderate</option>
                            <option value="high">High</option>
                        </select>
                    </div>
                    
                    <!-- Submit Button -->
                    <div class="form-group">
                        <button type="submit">Calculate Carbon Emission</button>
                    </div>
                </form>
            </div>
            
            <div class="result-container" id="result-container" style="${emission != null ? 'display:block;' : 'display:none;'}">
                <h2>Emission Calculation Result</h2>
                <p><strong>Region:</strong> ${region}</p>
                <p><strong>Purpose:</strong> ${purpose}</p>
                <p><strong>People:</strong> ${people}</p>
                <p><strong>Devices:</strong> ${devices}</p>
                <p><strong>Vehicle:</strong> ${vehicle}</p>
                <p><strong>Traffic:</strong> ${traffic}</p>
                <p><strong>Ventilation:</strong> ${ventilation}</p>
                <p><strong>Total Carbon Emission: ${emission} kg CO<sub>2</sub></strong></p>
                


<div style="margin-top: 30px; padding: 20px; border-radius: 10px; background-color: #f0f9ff; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
    <h2> Carbon Emission Report</h2>

    <p style="font-size: 18px;">
         <strong>Estimated Emission per Hour:</strong> 
        <span style="color: #007bff;"><strong>${emission}</strong> kg CO<sub>2</sub></span>
    </p>

    <p style="font-size: 18px;">
         <strong>Estimated Emission per Minute:</strong> 
        <span style="color: #28a745;"><strong>${emissionPerMinute}</strong> kg CO<sub>2</sub></span>
    </p>

    <% 
        double perMinute = (request.getAttribute("emissionPerMinute") != null)
            ? (Double) request.getAttribute("emissionPerMinute")
            : 0.0;

        if (perMinute < 0.05) {
    %>
        <p style="color: green;"> Excellent : Very low carbon impact.</p>
    <% } else if (perMinute < 0.1) { %>
        <p style="color: orange;">Moderate : Consider reducing device usage.</p>
    <% } else { %>
        <p style="color: red;"> High Emissions : Try minimizing electronics or travel.</p>
    <% } %>
  
    
</div> 
  <%
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
    String today = sdf.format(new java.util.Date());
%>

    <h2 class="suggestion">Personalized Suggestions:</h2>
				<!-- This part will display the suggestions dynamically -->
				<pre style=" overflow:auto">${suggestions}</pre>
	<form method="post" action="downloadCSV" style="margin-top: 20px;">
    <input type="hidden" name="region" value="${region}">
    <input type="hidden" name="purpose" value="${purpose}">
    <input type="hidden" name="people" value="${people}">
    <input type="hidden" name="devices" value="${devices}">
    <input type="hidden" name="vehicle" value="${vehicle}">
    <input type="hidden" name="traffic" value="${traffic}">
    <input type="hidden" name="ventilation" value="${ventilation}">
    <input type="hidden" name="emission" value="${emission}">
    <input type="hidden" name="emissionPerMinute" value="${emissionPerMinute}">
    <input type="hidden" name="submittedDate" value="<%= today %>">
    
    <button type="submit" style="padding: 10px 20px; font-size: 16px;">Download Report as CSV</button>
</form>
<!-- Back Button -->
<button onclick="showDashboard()" class="btn btn-primary">Back to Dashboard</button>

                
                
            </div>
        </div>
    </div>
    <script>
        function showForm() {
            document.getElementById('image-container').style.display = 'none';
            document.getElementById('cards').style.display = 'none';
            document.getElementById('form-container').style.display = 'block';
            document.getElementById('result-container').style.display = 'none';
        }

        function showDashboard() {
            document.getElementById('image-container').style.display = 'block';
            document.getElementById('cards').style.display = 'grid';
            document.getElementById('form-container').style.display = 'none';
            document.getElementById('result-container').style.display = 'none';
        }
        
        function showResult() {
            document.getElementById('image-container').style.display = 'none';
            document.getElementById('cards').style.display = 'none';
            document.getElementById('form-container').style.display = 'none';
            document.getElementById('result-container').style.display = 'block';
        }
        function showReport(){
        	document.getElementById('image-container').style.display = 'none';
            document.getElementById('cards').style.display = 'none';
            document.getElementById('form-container').style.display = 'none';
            document.getElementById('result-container').style.display = 'block';
		}
    </script>
   <script>
    // Initialize the region-device map as per your current structure
    const regionDeviceMap = {
        classroom: ['Fan', 'AC', 'Laptop', 'Projector', 'Charging Point', 'Lights'],
        home: ['Fan', 'AC', 'TV', 'Refrigerator', 'Laptop', 'Lights'],
        office: ['AC', 'Laptop', 'Printer', 'Lights'],
        kitchen: ['Microwave', 'Induction Stove', 'Exhaust Fan', 'Fridge', 'Lights'],
        garden: ['Lights'],
        industry: ['Fan', 'Machine', 'Compressor', 'Lights'],
        roadside: ['Street Lights'],
        market: ['AC', 'Fan', 'Lights'],
        concert: ['Speakers', 'Stage Lights', 'Projectors', 'Fans'],
        cafeteria: ['Refrigerator', 'Fan', 'AC', 'Lights'],
        pub: ['Lights', 'Speakers', 'Refrigerator'],
        conferencehall: ['AC', 'Projector', 'Mic System', 'Lights'],
        mall: ['AC', 'Elevator', 'Escalator', 'Lights'],
        restaurant: ['AC', 'Refrigerator', 'Microwave', 'Lights']
    };

    // Select the region dropdown and devices container
    const regionSelect = document.getElementById('region');
    const devicesContainer = document.getElementById('devices-container');

    // Function to update the device checkboxes based on the selected region
    function updateDeviceOptions() {
        const selectedRegion = regionSelect.value;
        const devices = regionDeviceMap[selectedRegion] || [];

        // Clear previous checkboxes
        devicesContainer.innerHTML = '';

        // Create checkboxes dynamically for each device
        devices.forEach(device => {
            const label = document.createElement('label');
            label.classList.add('device-checkbox-label');

            const checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.name = 'devices';
            checkbox.value = device.toLowerCase().replace(/\s+/g, '-'); // hyphenate device name for value

            label.appendChild(checkbox);
            label.appendChild(document.createTextNode(device));

            devicesContainer.appendChild(label);
            devicesContainer.appendChild(document.createElement('br')); // Add line break between checkboxes
        });
    }

    // Event listener to call updateDeviceOptions whenever the region is changed
    regionSelect.addEventListener('change', updateDeviceOptions);

    // Call updateDeviceOptions once when the page loads to populate the devices based on the default region
    document.addEventListener('DOMContentLoaded', updateDeviceOptions);
    
    
    let currentIndex = 0;

    function showTip(index) {
        const tipContainer = document.getElementById('tip');
        if (tipContainer && tips.length > 0) {
            tipContainer.textContent = tips[index];
        }
    }

    function nextTip() {
        currentIndex = (currentIndex + 1) % tips.length;
        showTip(currentIndex);
    }

    function prevTip() {
        currentIndex = (currentIndex - 1 + tips.length) % tips.length;
        showTip(currentIndex);
    }


    document.addEventListener('DOMContentLoaded', function () {
        // Show first tip
        showTip(currentIndex);

        // Button click handlers
        document.getElementById('nextBtn').addEventListener('click', nextTip);
        document.getElementById('prevBtn').addEventListener('click', prevTip);
    });

</script>

    
</body>
</html>