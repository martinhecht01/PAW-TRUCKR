<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<c:set var="locator" value="new org.webjars.WebJarAssetLocator()" />
<html>
<link>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link href="/css/main.css" rel="stylesheet"/>
</head>
<body>


<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Navbar</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Features</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Pricing</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled">Disabled</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container d-flex justify-content-center align-items-center vh-100">
    <div class="card border-0 shadow-sm rounded-3">
        <div class="row no-gutters">
            <div class="col-md-4">
                <img src="http://t2.gstatic.com/licensed-image?q=tbn:ANd9GcQNxLs9ztCGoYOAq9Lg-J6eEHaNgm1trwlfXEhXnKlvzgcztA7wunvdwbsd2vHmnORyvAYbsrpONdQxM2o96Ho" class="card-img rounded-start" alt="Your Image">
            </div>
            <div class="col-md-8">
                <div class="card-body">
                    <h5 class="card-title">Card Title</h5>
                    <p class="card-text"><strong>Origin:</strong> New York, NY</p>
                    <p class="card-text"><strong>Destination:</strong> Los Angeles, CA</p>
                    <p class="card-text"><strong>Departure Date:</strong> April 10, 2023</p>
                    <p class="card-text"><strong>Arrival Date:</strong> April 15, 2023</p>
                    <p class="card-text"><strong>Available Volume:</strong> 10 cubic meters</p>
                    <p class="card-text"><strong>Available Weight:</strong> 500 kg</p>
                    <div class="form-group mb-3">
                        <label for="emailInput"><strong>Email:</strong></label>
                        <input type="email" class="form-control" id="emailInput" placeholder="Enter your email">
                    </div>
                    <div class="text-end">
                        <button type="button" class="btn btn-primary mt-3">Make Reservation</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>