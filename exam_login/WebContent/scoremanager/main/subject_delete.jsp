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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>
			<form method="get" action="SubjectDeleteExecute.action">
			<input type="hidden" name="cd" value="${subject.cd}">
			<input type="hidden" name="school_cd" value="${school_cd}">
			<p>「${subject.name}(${subject.cd})」を削除してもよろしいですか</p>
				<div class="text-start mx-3">
					<button type="submit" class="btn btn-danger">削除</button>
				</div>
				<div class="text-start mx-3 mt-3">
					<a href="StudentList.action">戻る</a>
				</div>
			</form>
		</section>
	</c:param>
</c:import>