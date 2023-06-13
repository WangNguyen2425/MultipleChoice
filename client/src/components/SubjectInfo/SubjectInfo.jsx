import {Form, Input, Button } from "antd";
import "./SubjectInfo.scss";
import React from "react";
const SubjectInfo = ({
  onFinish,
  initialValues,
  infoHeader,
  btnText,
  loading,
}) => {

  return (
    <div className="subject-info">
      <p className="info-header">{infoHeader}</p>
      <Form
        name="info-subject-form"
        className="info-subject-form"
        initialValues={initialValues}
        onFinish={onFinish}
      >
        <div className="info-subject-header">Thông tin học phần</div>
        <Form.Item
          name="code"
          label="Code"
          colon={true}
          rules={[
            {
              required: true,
              message: "Please input the code!",
            }
          ]}
        >
          <Input placeholder="Enter the code" />
        </Form.Item>
        <Form.Item
          name="title"
          label="Title"
          colon={true}
          rules={[
            {
              required: true,
              message: "Please input the title!",
            },
          ]}
        >
          <Input placeholder="Enter user name" />
        </Form.Item>
        <Form.Item
          name="description"
          label="Description"
          colon={true}
          rules={[
            {
              required: true,
              message: "Please input the description!",
            },
          ]}
        >
          <Input placeholder="Enter the description" />
        </Form.Item>
        <Form.Item
          name="credit"
          label="Credit"
          colon={true}
          rules={[
            {
              required: true,
              message: "Please input the credit!",
            },
            {
              type: "number",
              message:"Credit has must be a number!"
            }
          ]}
        >
          <Input placeholder="Enter the credit" />
        </Form.Item>
        <Form.Item className="btn-info">
          <Button
            type="primary"
            htmlType="submit"
            loading={loading}
            style={{ width: 150, height: 50 }}
          >
            {btnText}
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};
export default SubjectInfo;
