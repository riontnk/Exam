<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

			<div class="border mx-3 mb-3 py-2 px-3 rounded">
				<!-- 科目情報 -->
				<form method="get" action="TestListSubjectExecute.action">
					<div class="row align-items-center"
						style="display: flex; flex-wrap: nowrap; gap: 16px; padding: 16px;">
						<div
							style="flex: 0; display: flex; align-items: center; justify-content: center; margin-right: 26px; min-width: 100px; height: 100%;">
							<span>科目情報</span>
						</div>
						<!-- 入学年度 -->
						<div class="col-2">
							<label class="form-label" for="student-f1-select">入学年度</label> <select
								class="form-select" id="student-f1-select" name="f1">
								<option value="0">--------</option>
								<c:forEach var="year" items="${ent_year_set}">
									<option value="${year}"
										<c:if test="${year==f1}">selected</c:if>>${year}</option>
								</c:forEach>
							</select>
						</div>
						<!-- クラス -->
						<div class="col-2">
							<label class="form-label" for="student-f2-select">クラス</label> <select
								class="form-select" id="student-f2-select" name="f2">
								<option value="0">--------</option>
								<c:forEach var="num" items="${class_num_set}">
									<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
								</c:forEach>
							</select>
						</div>
						<!-- 科目 -->
						<div class="col-4">
							<label class="form-label" for="student-f3-select">科目</label> <select
								class="form-select" id="student-f3-select" name="f3">
								<option value="0">--------</option>
								<c:forEach var="subject" items="${subject_set}">
									<option value="${subject.getCd()}"
										<c:if test="${subject.getCd()==f3}">selected</c:if>>${subject.getName()}</option>
								</c:forEach>
							</select>
						</div>
						<!-- 検索 -->
						<div style="min-width: 100px;">
							<button class="btn btn-secondary" id="filter-button1">検索</button>
						</div>
					</div>
				</form>

				<!-- 区切り線 -->
				<hr class="mx-3 my-2">

				<!-- 学生情報 -->
				<form method="get" action="TestListStudentExecute.action">
					<div class="row align-items-center"
						style="display: flex; flex-wrap: nowrap; gap: 16px; padding: 16px;">
						<div
							style="flex: 0; display: flex; align-items: center; justify-content: center; margin-right: 26px; min-width: 100px; height: 100%;">
							<span>学生情報</span>
						</div>
						<!-- 学生番号 -->
						<div class="col-6">
							<label class="form-label">学生番号</label> <input
								class="form-control" type="text" id="no" name="f4" value="${f4}"
								required maxlength="30" placeholder="学生番号を入力してください" />
						</div>
						<!-- 検索 -->
						<div style="min-width: 100px;">
							<button class="btn btn-secondary" id="filter-button2">検索</button>
						</div>
					</div>
				</form>
			</div>

			<!-- 結果 -->
			<c:choose>
				<c:when test="${tlsList.size()>0}">
					<div>科目：${subjectName }</div>
					<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>1回</th>
							<th>2回</th>
						</tr>
						<c:forEach var="tls" varStatus="status" items="${tlsList}">
							<tr>
								<td>${tls.entYear}</td>
								<td>${tls.classNum}</td>
								<td>${tls.studentNo}</td>
								<td>${tls.studentName}</td>
								<td>${tls.getPoint(1) != null ? tls.getPoint(1) : '-'}</td>
								<td>${tls.getPoint(2) != null ? tls.getPoint(2) : '-'}</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<div>成績情報が存在しませんでした</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>