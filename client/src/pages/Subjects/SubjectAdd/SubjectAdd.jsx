import React from "react";
import SubjectInfo from "../../../components/SubjectInfo/SubjectInfo";
const SubjectAdd = () => {
  return (
    <SubjectInfo
      infoHeader="Thêm học phần"
      btnText="Thêm"
      initialValues={{ remember: false }}
    />
  );
};
export default SubjectAdd;
