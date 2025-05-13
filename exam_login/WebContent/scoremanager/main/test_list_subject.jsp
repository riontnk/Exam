<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

            <div class="border border-bottom mx-3 mb-2 px-3 py-2 align-items-center rounded" id="filter">

                <!-- 科目で検索 -->
                <form action="TestListSubjectExecute.action" method="get">
                    <div class="row">
                        <div class="col-2 mt-4 text-center">
                            <p>科目情報</p>
                        </div>

                        <div class="col-2">
                            <label class="form-label" for="subject-f1-select">入学年度</label>
                            <select class="form-select" id="subject-f1-select" name="f1">
                                <option value="0">--------</option>
                                <c:forEach var="year" items="${entYearSet}">
                                    <option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-2">
                            <label class="form-label" for="subject-f2-select">クラス</label>
                            <select class="form-select" id="subject-f2-select" name="f2">
                                <option value="0">--------</option>
                                <c:forEach var="num" items="${cNumlist}">
                                    <option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-4">
                            <label class="form-label" for="subject-f3-select">科目</label>
                            <select class="form-select" id="subject-f3-select" name="f3">
                                <option value="0">--------</option>
                                <c:forEach var="subject" items="${list}">
                                    <option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-2 mt-3 text-center">
                            <input type="hidden" name="f" value="sj">
                            <button class="btn btn-secondary" id="subject-button">検索</button>
                        </div>
                        <div class="mt-2 text-warning">${errors.get("f1")}</div>
                    </div>
                </form>

                <div class="row border-bottom mx-0 mb-3 py-2 align-items-center"></div>

                <!-- 学生番号で検索 -->
                <form action="TestListStudentExecute.action" method="get">
                    <div class="row">
                        <div class="col-2 mt-3 text-center">
                            <p>学生情報</p>
                        </div>

                        <div class="col-4">
                            <label class="form-label" for="student-f4-input">学生番号</label>
                            <input class="form-control" type="text" id="student-f4-input" name="f4"
                                value="${f4}" required maxlength="30"
                                placeholder="学生番号を入力してください" />
                        </div>

                        <div class="col-2 mt-3 text-center">
                            <input type="hidden" name="f" value="st">
                            <button class="btn btn-secondary" id="student-button">検索</button>
                        </div>
                    </div>
                </form>
            </div>

            <!-- 科目別一覧表示 -->
			<c:choose>
				<c:when test="${errors.size()>0}">
					<label style="color: skyblue;">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</label>
				</c:when>
				<c:otherwise>
            		<c:choose>
                		<c:when test="${tlsList.size() > 0}">
                    		<div>科目：${subject_name}</div>
                    		<table class="table table-hover">
		                        <tr>
		                            <th>入学年度</th>
		                            <th>クラス</th>
		                            <th>学生番号</th>
		                            <th>氏名</th>
		                            <th>１回</th>
		                            <th>２回</th>
		                        </tr>
		                        <c:forEach var="test" items="${tlsList}">
		                            <tr>
		                                <td>${test.entYear}</td>
		                                <td>${test.classNum}</td>
		                                <td>${test.studentNo}</td>
		                                <td>${test.studentName}</td>
		                                <td>${test.getPoint(1)}</td>
		                                <td>
		                                    <c:choose>
		                                        <c:when test="${test.getPoint(2) != -1}">
		                                            ${test.getPoint(2)}
		                                        </c:when>
		                                        <c:otherwise>-</c:otherwise>
		                                    </c:choose>
		                                </td>
		                            </tr>
		                        </c:forEach>
                    		</table>
                		</c:when>
                		<c:otherwise>
                    		<div>学生情報が存在しませんでした</div>
                		</c:otherwise>
            		</c:choose>
       			</c:otherwise>
			</c:choose>
		</section>
    </c:param>
</c:import>
