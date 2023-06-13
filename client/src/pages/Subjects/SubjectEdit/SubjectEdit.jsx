import React from "react";
import SubjectInfo from "../../../components/SubjectInfo/SubjectInfo";
const SubjectEdit = () => {
	return (
    <SubjectInfo
      infoHeader="Sửa thông tin học phần"
      btnText="Thêm"
      initialValues={{ remember: false }}
    />
  );
};
export default SubjectEdit;
