package seki.com.doyouworkout.ui

data class Training(val id: Int,
                    val name: String,
                    val trainingNameId: Int = 0,
                    val isUsed: Boolean,
                    val customName: String,
                    val isCustom: Boolean,
                    val isDeleted: Boolean)