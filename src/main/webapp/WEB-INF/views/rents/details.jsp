<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<%@ include file="/WEB-INF/views/common/header.jsp"%>
		<!-- Left side column. contains the logo and sidebar -->
		<%@ include file="/WEB-INF/views/common/sidebar.jsp"%>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Main content -->
			<section class="content">

				<div class="row">
					<div class="col-md-3">

						<!-- Profile Image -->
						<div class="box box-primary">
							<div class="box-body box-profile">
								<h3 class="profile-username text-center">Reservation
									${reservation_id}</h3>

								<ul class="list-group list-group-unbordered">
									<li class="list-group-item"><b>Debut</b> <a
										class="pull-right">${reservation_debut}</a></li>
									<li class="list-group-item"><b>Fin</b> <a
										class="pull-right">${reservation_fin}</a></li>
								</ul>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div>
					<!-- /.col -->
					<div class="col-md-9">
						<div class="nav-tabs-custom">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#rents" data-toggle="tab">Informations</a></li>
							</ul>
							<div class="tab-content">
								<div class="active tab-pane" id="rents">
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tr>
												<th></th>
												<th style="width: 10px">#</th>
												<th>Nom</th>
												<th>Prenom</th>
												<th>Email</th>
												<th>Date de naissance</th>
											</tr>
											<tr>
												<th>Client :</th>
												<td><c:out value="${client_id}" /></td>
												<td><c:out value="${client_nom}" /></td>
												<td><c:out value="${client_prenom}" /></td>
												<td><c:out value="${client_email}" /></td>
												<td><c:out value="${client_naissance}" /></td>
											</tr>
										</table>
										<br>
										<table class="table table-striped">
											<tr>
												<th></th>
												<th style="width: 10px">#</th>
												<th>Constructeur</th>
												<th>Nombre de places</th>
											</tr>
											<tr>
												<th>Voiture :</th>
												<td><c:out value="${vehicle_id}" /></td>
												<td><c:out value="${vehicle_constructeur}" /></td>
												<td><c:out value="${vehicle_nb_places}" /></td>
											</tr>
										</table>
									</div>
								</div>
							</div>
							<!-- /.tab-content -->
						</div>
						<!-- /.nav-tabs-custom -->
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->

			</section>
			<!-- /.content -->
		</div>

		<%@ include file="/WEB-INF/views/common/footer.jsp"%>
	</div>
	<!-- ./wrapper -->

	<%@ include file="/WEB-INF/views/common/js_imports.jsp"%>
</body>
</html>
