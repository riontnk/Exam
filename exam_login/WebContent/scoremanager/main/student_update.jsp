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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>
			<form method="get" action="StudentUpdateExecute.action">
				<div class="col-11 mx-3 mb-3">
					<label class="form-label" for="student-f1-select">入学年度</label> <input
						type="text" class="form-control border-0 shadow-none"
						value="${student.entYear}" readonly> <input type="hidden"
						name="ent_year" value="${student.entYear}">
				</div>

				<div class="col-11 mx-3 mb-3">
					<label class="form-label" for="student-number-input">学生番号</label> <input
						type="text" class="form-control border-0 shadow-none" id="student-number-input"
						value="${student.no}" readonly> <input type="hidden"
						name="no" value="${student.no}">
				</div>

				<div class="col-11 mx-3 mb-3">
					<label class="form-label" for="student-name-input">氏名</label> <input
						type="text" class="form-control" id="student-name-input"
						name="name" value="${student.name}" placeholder="氏名を入力してください"
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

				<div class="col-11 mx-3 mb-3">
					<label class="form-check-label" for="student-f3-check">在学中
						<input class="form-check-input" type="checkbox"
						id="student-f3-check" name="is_attend" <c:if test="${is_attend }">checked</c:if> />
					</label>
				</div>

				<div class="text-start mx-3">
					<button type="submit" class="btn btn-primary">変更</button>
				</div>
				<div class="text-start mx-3 mt-3">
					<a href="StudentList.action">戻る</a>
				</div>
			</form>
		</section>
	</c:param>
</c:import>