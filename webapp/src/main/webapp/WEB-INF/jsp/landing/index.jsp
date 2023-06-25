<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="components" tagdir="/WEB-INF/tags" %>

<html>
<link>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
    <link href="<c:url value="/css/main.css"/>" rel="stylesheet"/>


<head>
    <title>Truckr</title>
    <link rel="icon" type="image/x-icon" href="https://i.ibb.co/Qb69pVJ/Truckr-Favicon.png"></head>
<body>
<components:navBar/>
<div class="card-container heroColor">
    <div class="container">
        <div class="row p-4 pt-5 pb-0 pe-lg-0 align-items-center rounded-3">
            <div class="col-lg-7 p-3 p-lg-5">
                <h1 class="display-1 mb-2 fw-bolder lh-1"><spring:message code="Truckr"/></h1>
                <h1 class="display-6 mb-2 fw-medium lh-1"><spring:message code="LandingMainMessage"/></h1>
                <p class="lead mt-3 light" ><spring:message code="LandingMainSubMessage"/></p>
                <div class="d-grid gap-2 d-md-flex justify-content-md-start mb-4 mb-lg-3">
                    <a href="<c:url value="/trips/browse"/>" type="button" class="btn btn-lg px-4 me-md-2 w-75" style="background-color: #142D4C; color: white;"><spring:message code="BookATrucker"/></a>
                    <a href="<c:url value="/requests/browse"/>" type="button" class="btn btn-lg px-4 me-md-2 w-75" style="background-color: #142D4C; color: white;"><spring:message code="DriveWithTruckr"/></a>
                </div>
            </div>
<%--            <div class="col-lg-4 offset-lg-1 p-0 overflow-hidden shadow-lg">--%>
<%--                <img class="rounded-lg-3" src="https://hips.hearstapps.com/hmg-prod/images/volvo-vnr-electric-6x2-with-reefer-trailer-passenger-side-view-on-the-road-daytime-shot-1607106606.jpg?crop=0.643xw:0.988xh;0.204xw,0&resize=1200:*"  alt="" width="780" style="transform: translate(-35%, 0)">--%>
<%--            </div>--%>
        </div>
    </div>
    <div style="margin-top: auto">
        <components:waveDivider/>
    </div>
</div>
<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
    <symbol id="bootstrap" viewBox="0 0 118 94">
        <title>Bootstrap</title>
        <path fill-rule="evenodd" clip-rule="evenodd" d="M24.509 0c-6.733 0-11.715 5.893-11.492 12.284.214 6.14-.064 14.092-2.066 20.577C8.943 39.365 5.547 43.485 0 44.014v5.972c5.547.529 8.943 4.649 10.951 11.153 2.002 6.485 2.28 14.437 2.066 20.577C12.794 88.106 17.776 94 24.51 94H93.5c6.733 0 11.714-5.893 11.491-12.284-.214-6.14.064-14.092 2.066-20.577 2.009-6.504 5.396-10.624 10.943-11.153v-5.972c-5.547-.529-8.934-4.649-10.943-11.153-2.002-6.484-2.28-14.437-2.066-20.577C105.214 5.894 100.233 0 93.5 0H24.508zM80 57.863C80 66.663 73.436 72 62.543 72H44a2 2 0 01-2-2V24a2 2 0 012-2h18.437c9.083 0 15.044 4.92 15.044 12.474 0 5.302-4.01 10.049-9.119 10.88v.277C75.317 46.394 80 51.21 80 57.863zM60.521 28.34H49.948v14.934h8.905c6.884 0 10.68-2.772 10.68-7.727 0-4.643-3.264-7.207-9.012-7.207zM49.948 49.2v16.458H60.91c7.167 0 10.964-2.876 10.964-8.281 0-5.406-3.903-8.178-11.425-8.178H49.948z"></path>
    </symbol>
    <symbol id="truck" viewBox="0 0 16 16">
        <path d="M0 3.5A1.5 1.5 0 0 1 1.5 2h9A1.5 1.5 0 0 1 12 3.5V5h1.02a1.5 1.5 0 0 1 1.17.563l1.481 1.85a1.5 1.5 0 0 1 .329.938V10.5a1.5 1.5 0 0 1-1.5 1.5H14a2 2 0 1 1-4 0H5a2 2 0 1 1-3.998-.085A1.5 1.5 0 0 1 0 10.5v-7zm1.294 7.456A1.999 1.999 0 0 1 4.732 11h5.536a2.01 2.01 0 0 1 .732-.732V3.5a.5.5 0 0 0-.5-.5h-9a.5.5 0 0 0-.5.5v7a.5.5 0 0 0 .294.456zM12 10a2 2 0 0 1 1.732 1h.768a.5.5 0 0 0 .5-.5V8.35a.5.5 0 0 0-.11-.312l-1.48-1.85A.5.5 0 0 0 13.02 6H12v4zm-9 1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm9 0a1 1 0 1 0 0 2 1 1 0 0 0 0-2z"></path>
    </symbol>
    <symbol id="piggy-bank" viewBox="0 0 16 16">
        <path d="M5 6.25a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0zm1.138-1.496A6.613 6.613 0 0 1 7.964 4.5c.666 0 1.303.097 1.893.273a.5.5 0 0 0 .286-.958A7.602 7.602 0 0 0 7.964 3.5c-.734 0-1.441.103-2.102.292a.5.5 0 1 0 .276.962z"></path>
        <path fill-rule="evenodd" d="M7.964 1.527c-2.977 0-5.571 1.704-6.32 4.125h-.55A1 1 0 0 0 .11 6.824l.254 1.46a1.5 1.5 0 0 0 1.478 1.243h.263c.3.513.688.978 1.145 1.382l-.729 2.477a.5.5 0 0 0 .48.641h2a.5.5 0 0 0 .471-.332l.482-1.351c.635.173 1.31.267 2.011.267.707 0 1.388-.095 2.028-.272l.543 1.372a.5.5 0 0 0 .465.316h2a.5.5 0 0 0 .478-.645l-.761-2.506C13.81 9.895 14.5 8.559 14.5 7.069c0-.145-.007-.29-.02-.431.261-.11.508-.266.705-.444.315.306.815.306.815-.417 0 .223-.5.223-.461-.026a.95.95 0 0 0 .09-.255.7.7 0 0 0-.202-.645.58.58 0 0 0-.707-.098.735.735 0 0 0-.375.562c-.024.243.082.48.32.654a2.112 2.112 0 0 1-.259.153c-.534-2.664-3.284-4.595-6.442-4.595zM2.516 6.26c.455-2.066 2.667-3.733 5.448-3.733 3.146 0 5.536 2.114 5.536 4.542 0 1.254-.624 2.41-1.67 3.248a.5.5 0 0 0-.165.535l.66 2.175h-.985l-.59-1.487a.5.5 0 0 0-.629-.288c-.661.23-1.39.359-2.157.359a6.558 6.558 0 0 1-2.157-.359.5.5 0 0 0-.635.304l-.525 1.471h-.979l.633-2.15a.5.5 0 0 0-.17-.534 4.649 4.649 0 0 1-1.284-1.541.5.5 0 0 0-.446-.275h-.56a.5.5 0 0 1-.492-.414l-.254-1.46h.933a.5.5 0 0 0 .488-.393zm12.621-.857a.565.565 0 0 1-.098.21.704.704 0 0 1-.044-.025c-.146-.09-.157-.175-.152-.223a.236.236 0 0 1 .117-.173c.049-.027.08-.021.113.012a.202.202 0 0 1 .064.199z"></path>
    </symbol>
    <symbol id="people" viewBox="0 0 16 16">
        <path d="M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1h8Zm-7.978-1A.261.261 0 0 1 7 12.996c.001-.264.167-1.03.76-1.72C8.312 10.629 9.282 10 11 10c1.717 0 2.687.63 3.24 1.276.593.69.758 1.457.76 1.72l-.008.002a.274.274 0 0 1-.014.002H7.022ZM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4Zm3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0ZM6.936 9.28a5.88 5.88 0 0 0-1.23-.247A7.35 7.35 0 0 0 5 9c-4 0-5 3-5 4 0 .667.333 1 1 1h4.216A2.238 2.238 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816ZM4.92 10A5.493 5.493 0 0 0 4 13H1c0-.26.164-1.03.76-1.724.545-.636 1.492-1.256 3.16-1.275ZM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0Zm3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4Z"></path>
    </symbol>
</svg>

<div class="px-5 w-100 text-center">
    <h1 class="display-6 mb-2 fw-medium lh-1">How Truckr works</h1>
    <ul class="nav nav-underline justify-content-center" id="myTabs" role="tablist">
        <li class="nav-item mx-2" role="presentation">
            <button class="nav-link active" id="tab1-tab" data-bs-toggle="tab" data-bs-target="#tab1" type="button" role="tab" aria-controls="tab1" aria-selected="true">For Truckers</button>
        </li>
        <li class="nav-item mx-2" role="presentation">
            <button class="nav-link" id="tab2-tab" data-bs-toggle="tab" data-bs-target="#tab2" type="button" role="tab" aria-controls="tab2" aria-selected="false">For Providers</button>
        </li>
    </ul>

    <div class="tab-content" id="myTabContent">
        <div class="tab-pane fade show active" id="tab1" role="tabpanel" aria-labelledby="tab1-tab">
            <!-- Content for Tab 1 goes here -->
            <div class="tripCards w-100 px-5 pt-5 justify-content-center m-auto">

                <div class="feature col">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Browse cargo</h3>
                        </div>
                        <div class="panel-body">
                            Browse the cargo that is available. Filter however is more convinient for you
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Select the best cargo option for you</h3>
                        </div>
                        <div class="panel-body">
                            Select the cargo that you want to ship and send an offer
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">One more step...</h3>
                        </div>
                        <div class="panel-body">
                            Wait for the cargo provider to confirm your offer. You can always modify it or send a counteroffer if you want
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Ship it!</h3>
                        </div>
                        <div class="panel-body">
                            Once the order is confirmed, get in touch with the provider and ship the cargo
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <div class="tab-pane fade" id="tab2" role="tabpanel" aria-labelledby="tab2-tab">
            <!-- Content for Tab 2 goes here -->
            <div class="tripCards w-100 px-5 pt-5 justify-content-center m-auto">
                <div class="feature col">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Search for a trip</h3>
                        </div>
                        <div class="panel-body">
                            Create a custom search according to your needs
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Select the best trip for you</h3>
                        </div>
                        <div class="panel-body">
                            Select the trip that best fits your needs and send an offer
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">One more step...</h3>
                        </div>
                        <div class="panel-body">
                            Wait for the trucker to confirm your offer. You can always modify it or send a counteroffer if you want
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Ship it!</h3>
                        </div>
                        <div class="panel-body">
                            Once the trucker confirms your offer you can get in touch with him and ship your cargo
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>




<div class="container px-4 pb-5" id="featured-3">
    <div class="row g-4 row-cols-1 pb-5 row-cols-lg-3">
        <div class="feature col">
            <div class="feature-icon d-inline-flex align-items-center justify-content-center text-bg-primary bg-gradient fs-2 mb-3">
                <svg class="bi" width="1em" height="1em"><use xlink:href="#people"></use></svg>
            </div>
            <h3 class="fs-2"><spring:message code="TrustworthyTruckers"/></h3>
            <p class="light"><spring:message code="TrustworthyTruckersMessage"/></p>
        </div>
        <div class="feature col">
            <div class="feature-icon d-inline-flex align-items-center justify-content-center text-bg-primary bg-gradient fs-2 mb-3">
                <svg class="bi" width="1em" height="1em"><use xlink:href="#piggy-bank"></use></svg>
            </div>
            <h3 class="fs-2"><spring:message code="EconomicSolutions"/></h3>
            <p class="light"><spring:message code="EconomicSolutionsMessage"/></p>
        </div>
        <div class="feature col">
            <div class="feature-icon d-inline-flex align-items-center justify-content-center text-bg-primary bg-gradient fs-2 mb-3">
                <svg class="bi" width="1em" height="1em"><use xlink:href="#truck"></use></svg>
            </div>
            <h3 class="fs-2"><spring:message code="ExploitReturns"/></h3>
            <p class="light"><spring:message code="ExploitReturnsMessage"/></p>
        </div>
    </div>
</div>
</body>
<components:footer/>
</html>