import anorm.Pk
 
case class Item( 
    var id: Pk[Long], 
    var name: String, 
    var shortDesc: String 
)