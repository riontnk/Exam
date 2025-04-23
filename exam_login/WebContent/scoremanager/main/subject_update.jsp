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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
			<form method="get" action="SubjectUpdateExecute.action">
				<div class="col-11 mx-3 mb-3">
					<label class="form-label" for="student-f1-select">科目コード</label> <input
						type="text" class="form-control border-0 shadow-none"
						value="${subject.cd}" readonly> <input type="hidden"
						name="cd" value="${subject.cd}">
						<div class="mt-2 text-warning">${errors.get("cd")}</div>
				</div>

				<div class="col-11 mx-3 mb-3">
					<label class="form-label" for="student-name-input">科目名</label> <input
						type="text" class="form-control" id="student-name-input"
						name="name" value="${subject.name}" placeholder="科目名を入力してください"
						maxlength="20" required>
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