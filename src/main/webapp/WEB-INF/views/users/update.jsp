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
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>Utilisateurs</h1>
			</section>

			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-md-12">
						<!-- Horizontal Form -->
						<div class="box">
							<!-- form start -->
							<form class="form-horizontal" method="post">
								<div class="box-body">
									<div class="form-group">
										<label for="nomU" class="col-sm-2 control-label">Nom</label>

										<div class="col-sm-10">
											<input type="text" class="form-control" id="nomU"
												name="nomU" placeholder="Nom" value="${nomU}"
												required>
										</div>
									</div>
									<div class="form-group">
										<label for="prenomU" class="col-sm-2 control-label">Pr&eacutenom</label>

										<div class="col-sm-10">
											<input type="text" class="form-control" id="prenomU"
												name="prenomU" placeholder="Prenom"
												value="${prenomU}" required>
										</div>
									</div>
									<div class="form-group">
										<label for="emailU" class="col-sm-2 control-label">Email</label>

										<div class="col-sm-10">
											<input type="email" class="form-control" id="emailU"
												name="emailU" placeholder="Email" value="${emailU}" required>
										</div>
									</div>
									<div class="form-group">
										<label for="naissanceU" class="col-sm-2 control-label">Date
											de naissance</label>

										<div class="col-sm-10">
											<input type="date" class="form-control" id="naissanceU"
												name="naissanceU" placeholder="Date de naissance"
												value="${naissanceU}" required>
										</div>
									</div>
								</div>
								<!-- /.box-body -->
								<div class="box-footer">
									<button type="submit" name="action" value="1"
										class="btn btn-info pull-right">Modifier</button>
									<button type="submit" name="action" value=""
										class="btn btn-danger pull-right">Annuler</button>
								</div>
								<!-- /.box-footer -->
							</form>
						</div>
						<!-- /.box -->
					</div>
					<!-- /.col -->
				</div>
			</section>
			<!-- /.content -->
		</div>

		<%@ include file="/WEB-INF/views/common/footer.jsp"%>
	</div>
	<!-- ./wrapper -->

	<%@ include file="/WEB-INF/views/common/js_imports.jsp"%>
</body>
</html>
