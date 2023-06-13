import React from "react";
import SubjectInfo from "../../../components/SubjectInfo/SubjectInfo";
const SubjectAdd = () => {
	const onFinish = (values) => {
		console.log(values);
	};
	return (
		<SubjectInfo
			infoHeader="Thêm học phần"
			btnText="Thêm"
			initialValues={{ remember: false }}
			onFinish={onFinish}
		/>
	);
};
export default SubjectAdd;
