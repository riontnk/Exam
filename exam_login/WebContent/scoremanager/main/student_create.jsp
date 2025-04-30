<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
得点管理システム
</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>
			<form method="get" action="StudentCreateExecute.action">
				<div class="col-11 mx-3 mb-3">
					<label class="form-label" for="student-f1-select">入学年度</label> <select
						class="form-select" id="student-f1-select" name="ent_year">
						<option value="0">--------</option>
						<c:forEach var="year" items="${ent_year_set}">
							<option value="${year }"
								<c:if test="${year==ent_year }">selected</c:if>>${year }</option>
						</c:forEach>
					</select>
					<div class="mt-2 text-warning">${errors.get("ent_year")}</div>
				</div>

				<div class="col-11 mx-3 mb-3">
					<label class="form-label" for="student-number-input">学生番号</label> <input
						type="text" class="form-control" id="student-number-input"
						name="no" value="${no}" placeholder="学生番号を入力してください" maxlength="10"
						required>
					<div class="mt-2 text-warning">${errors.get("no")}</div>
				</div>

				<div class="col-11 mx-3 mb-3">
					<label class="form-label" for="student-name-input">氏名</label> <input
						type="text" class="form-control" id="student-name-input"
						name="name" value="${name}" placeholder="氏名を入力してください"
						maxlength="30" required>
				</div>

				<div class="col-11 mx-3 mb-3">
					<label class="form-label" for="student-f2-select">クラス</label> <select
						class="form-select" id="student-f2-select" name="class_num">
						<c:forEach var="class_num" items="${class_num_set}">
							<option value="${class_num}">${class_num}</option>
						</c:forEach>
					</select>
				</div>

				<div class="text-start mx-3">
					<button type="submit" class="btn btn-secondary">登録して終了</button>
				</div>
				<div class="text-start mx-3 mt-3">
					<a href="StudentList.action">戻る</a>
				</div>
			</form>
		</section>
	</c:param>
</c:import>