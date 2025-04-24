<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

            <!-- エラー -->
            <c:if test="${not empty errors}">
                <div style="color: red; margin-bottom: 16px;">
                    <c:forEach var="error" items="${errors}">
                        ${error.value}<br>
                    </c:forEach>
                </div>
            </c:if>

            <!-- Form 1-->
            <form method="get" action="TestList.action">
                <div class="row border mx-3 mb-3 py-2 px-3 align-items-end rounded" style="display: flex; flex-wrap: nowrap; gap: 16px; border: 1px solid #ccc; padding: 16px;">
                    <div style="flex: 0; display: flex; align-items: center; justify-content: center; margin-right: 26px; margin-bottom: 10px; flex-direction: row; min-width: 100px;">
                        <span style="font-weight: bold; color: #000;">科目情報</span>
                    </div>
                    <!-- 入学年度 -->
                    <div style="flex: 1;">
                        <label class="form-label" for="student-f1-select">入学年度</label>
                        <select class="form-select" id="student-f1-select" name="f1">
                            <option value="0">--------</option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <!-- クラス -->
                    <div style="flex: 1;">
                        <label class="form-label" for="student-f2-select">クラス</label>
                        <select class="form-select" id="student-f2-select" name="f2">
                            <option value="0">--------</option>
                            <c:forEach var="num" items="${class_num_set}">
                                <option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <!-- 科目 -->
                    <div style="flex: 1;">
                        <label class="form-label" for="student-f3-select">科目</label>
                        <select class="form-select" id="student-f3-select" name="subject">
                            <option value="0">--------</option>
                            <c:forEach var="subject" items="${subject_set}">
                                <option value="${subject}" <c:if test="${subject==selected_subject}">selected</c:if>>${subject}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <!-- 検索 -->
                    <div style="flex: 0; margin-left: 20px;">
                        <button class="btn btn-secondary mt-4"id="filter-button1">検索</button>
                    </div>
                </div>
            </form>

            <!-- Form 2: -->
            <form method="get" action="TestList.action">
                <div class="row border mx-3 mb-3 py-2 px-3 align-items-end rounded" style="display: flex; flex-wrap: nowrap; gap: 16px; border: 1px solid #ccc; padding: 16px; margin-top: 20px;">
                    <div style="flex: 0; display: flex; align-items: center; justify-content: center; margin-right: 26px; margin-bottom: 10px; flex-direction: row; min-width: 100px;">
                        <span style="font-weight: bold; color: #000;">学生情報</span>
                    </div>
                    <!-- 学生番号 -->
                    <div style="flex: 1;">
                        <label for="no">学生番号</label><br>
                        <input class="form-control" type="text" id="no" name="no" value="${no}" required maxlength="30" placeholder="学生番号を入力してください" />
                    </div>
                    <!-- 検索 -->
                    <div style="flex: 0; margin-left: 20px;">
                        <button class="btn btn-secondary mt-4" id="filter-button2">検索</button>
                    </div>
                </div>
            </form>

            <!-- 結果 -->
            <c:choose>
                <c:when test="${students.size()>0}">
                    <div>検索結果:${students.size()}件</div>
                    <table class="table table-hover">
                        <tr>
                            <th>入学年度</th>
                            <th>クラス</th>
                            <th>学生番号</th>
                            <th>氏名</th>
                            <th>一回</th>
                            <th>二回</th>

                            <th></th>
                            <th></th>
                        </tr>
                        <c:forEach var="student" items="${students}">
                            <tr>
                                <td>${student.entYear}</td>
                                <td>${student.classNum}</td>
                                <td>${student.no}</td>
                                <td>${student.name}</td>
								<td>${student.point1 != null ? student.point1 : '-'}</td>
								<td>${student.point2 != null ? student.point2 : '-'}</td>
                                <td> </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>
                <c:otherwise>
                    <div style="color: skyblue; font-size: 0.85rem; font-weight: 800;">科目情報または学生情報を入力して検索ボタンをクリックしてください</div>
                </c:otherwise>
            </c:choose>
        </section>
    </c:param>
</c:import>