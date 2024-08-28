resource "aws_iam_group" "devops_group" {
  name = "devops"
}

resource "aws_iam_group_membership" "devops" {
  name = aws_iam_group.devops_group.name

  users = [
    aws_iam_user.sunjoo_kim.name
  ]

  group = aws_iam_group.devops_group.name
}

resource "aws_iam_group_policy" "devops_policy" {
  name = "devops-group-policy"
  group = aws_iam_group.devops_group.name
  policy = <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": "*",
            "Resource": "*"
        }
    ]
}
EOF
}